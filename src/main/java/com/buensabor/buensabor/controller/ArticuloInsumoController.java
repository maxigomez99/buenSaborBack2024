package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.articulo.ArticuloInsumoDto;
import com.buensabor.buensabor.entities.ArticuloInsumo;
import com.buensabor.buensabor.entities.ImagenArticulo;
import com.buensabor.buensabor.entities.Categoria;
import com.buensabor.buensabor.entities.UnidadMedida;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.errores.ApiError;
import com.buensabor.buensabor.service.IArticuloInsumoService;
import com.buensabor.buensabor.service.ICategoriaService;
import com.buensabor.buensabor.service.IUnidadMedidaService;
import com.buensabor.buensabor.service.ISucursalService;
import com.buensabor.buensabor.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/articulos-insumo")
public class ArticuloInsumoController {

    @Autowired
    private IArticuloInsumoService articuloInsumoService;

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IUnidadMedidaService unidadMedidaService;

    @Autowired
    private ISucursalService sucursalService;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    //region CRUD Basico
    @GetMapping("/")
    public ResponseEntity<?> buscarTodos() {
        try {
            return ResponseEntity.ok(articuloInsumoService.mostrarLista());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articuloInsumoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/crear-con-imagenes")
    public ResponseEntity<?> crearArticuloConImagenes(@RequestBody ArticuloInsumoDto articuloDto) {
        try {
            // Construir la entidad ArticuloInsumo
            ArticuloInsumo.ArticuloInsumoBuilder<?, ?> builder = ArticuloInsumo.builder()
                    .denominacion(articuloDto.getDenominacion())
                    .descripcion(articuloDto.getDescripcion())
                    .codigo(articuloDto.getCodigo())
                    .precioVenta(articuloDto.getPrecioVenta())
                    .precioCompra(articuloDto.getPrecioCompra())
                    .stockActual(articuloDto.getStockActual())
                    .stockMaximo(articuloDto.getStockMaximo())
                    .esParaElaborar(articuloDto.getEsParaElaborar())
                    .stockMinimo(articuloDto.getStockMinimo());

            // Buscar y asignar las relaciones
            if (articuloDto.getCategoriaId() != null) {
                Categoria categoria = categoriaRepository.findById(articuloDto.getCategoriaId())
                    .orElseThrow(() -> new Exception("Categoría no encontrada"));
                builder.categoria(categoria);
            }

            if (articuloDto.getUnidadMedidaId() != null) {
                UnidadMedida unidadMedida = unidadMedidaService.findById(articuloDto.getUnidadMedidaId());
                if (unidadMedida == null) {
                    throw new Exception("Unidad de medida no encontrada");
                }
                builder.unidadMedida(unidadMedida);
            }

            if (articuloDto.getSucursalId() != null) {
                Sucursal sucursal = sucursalService.traerPorId(articuloDto.getSucursalId());
                builder.sucursal(sucursal);
            }

            ArticuloInsumo articuloInsumo = builder.build();

            // Procesar las imágenes si existen - GUARDAR EN BASE64 DIRECTAMENTE
            if (articuloDto.getImagenes() != null && !articuloDto.getImagenes().isEmpty()) {
                Set<ImagenArticulo> imagenes = new HashSet<>();
                for (String imagenBase64 : articuloDto.getImagenes()) {
                    ImagenArticulo imagen = ImagenArticulo.builder()
                            .url(imagenBase64) // Guardar directamente el base64 completo
                            .articulo(articuloInsumo)
                            .build();
                    imagenes.add(imagen);
                }
                articuloInsumo.setImagenes(imagenes);
            }

            // Usar el nuevo método que mantiene las imágenes en base64
            ArticuloInsumo nuevoArticulo = articuloInsumoService.cargarConImagenesBase64(articuloInsumo);
            return ResponseEntity.ok(articuloInsumoService.buscarPorIdBase64(nuevoArticulo.getId()));

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/editar-con-imagenes/{id}")
    public ResponseEntity<?> editarArticuloConImagenes(@PathVariable Long id, @RequestBody ArticuloInsumoDto articuloDto) {
        try {
            // Construir artículo con los nuevos datos
            ArticuloInsumo.ArticuloInsumoBuilder<?, ?> builder = ArticuloInsumo.builder()
                    .id(id)
                    .denominacion(articuloDto.getDenominacion())
                    .descripcion(articuloDto.getDescripcion())
                    .codigo(articuloDto.getCodigo())
                    .precioVenta(articuloDto.getPrecioVenta())
                    .precioCompra(articuloDto.getPrecioCompra())
                    .stockActual(articuloDto.getStockActual())
                    .stockMaximo(articuloDto.getStockMaximo())
                    .esParaElaborar(articuloDto.getEsParaElaborar())
                    .stockMinimo(articuloDto.getStockMinimo());

            // Actualizar relaciones si se proporcionan
            if (articuloDto.getCategoriaId() != null) {
                Categoria categoria = categoriaRepository.findById(articuloDto.getCategoriaId())
                    .orElseThrow(() -> new Exception("Categoría no encontrada"));
                builder.categoria(categoria);
            }

            if (articuloDto.getUnidadMedidaId() != null) {
                UnidadMedida unidadMedida = unidadMedidaService.findById(articuloDto.getUnidadMedidaId());
                if (unidadMedida == null) {
                    throw new Exception("Unidad de medida no encontrada");
                }
                builder.unidadMedida(unidadMedida);
            }

            if (articuloDto.getSucursalId() != null) {
                Sucursal sucursal = sucursalService.traerPorId(articuloDto.getSucursalId());
                builder.sucursal(sucursal);
            }

            ArticuloInsumo articuloActualizado = builder.build();

            // Actualizar imágenes si se proporcionan - MANTENER EN BASE64
            if (articuloDto.getImagenes() != null) {
                Set<ImagenArticulo> nuevasImagenes = new HashSet<>();
                for (String imagenBase64 : articuloDto.getImagenes()) {
                    ImagenArticulo imagen = ImagenArticulo.builder()
                            .url(imagenBase64) // Guardar directamente el base64 completo
                            .articulo(articuloActualizado)
                            .build();
                    nuevasImagenes.add(imagen);
                }
                articuloActualizado.setImagenes(nuevasImagenes);
            }

            // Usar el nuevo método que mantiene las imágenes en base64
            articuloInsumoService.actualizarConImagenesBase64(id, articuloActualizado);
            return ResponseEntity.ok(articuloInsumoService.buscarPorIdBase64(id));

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articuloInsumoService.deleteById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//endregion


    @PostMapping("/reactivate/{id}")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> reactivate(@PathVariable Long id) {
        try {
            articuloInsumoService.reactivate(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/toggle-estado")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> toggleEstado(@PathVariable Long id) {
        try {
            ArticuloInsumo articuloActualizado = articuloInsumoService.toggleEstado(id);
            String mensaje = articuloActualizado.isEliminado() ?
                "Artículo insumo desactivado exitosamente" :
                "Artículo insumo activado exitosamente";

            return ResponseEntity.ok().body(Map.of(
                "mensaje", mensaje,
                "articuloInsumo", articuloActualizado
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/taer-todo/")
    public ResponseEntity<?> traerTodo() {
        try {
            return ResponseEntity.ok(articuloInsumoService.traerTodo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}