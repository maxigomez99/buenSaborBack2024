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

            // Procesar las imágenes si existen
            if (articuloDto.getImagenes() != null && !articuloDto.getImagenes().isEmpty()) {
                Set<ImagenArticulo> imagenes = new HashSet<>();
                for (String imagenBase64 : articuloDto.getImagenes()) {
                    ImagenArticulo imagen = ImagenArticulo.builder()
                            .url(imagenBase64)
                            .articulo(articuloInsumo)
                            .build();
                    imagenes.add(imagen);
                }
                articuloInsumo.setImagenes(imagenes);
            }

            ArticuloInsumo nuevoArticulo = articuloInsumoService.cargar(articuloInsumo);
            return ResponseEntity.ok(articuloInsumoService.buscarPorId(nuevoArticulo.getId()));

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/editar-con-imagenes/{id}")
    public ResponseEntity<?> editarArticuloConImagenes(@PathVariable Long id, @RequestBody ArticuloInsumoDto articuloDto) {
        try {
            ArticuloInsumo articuloExistente = articuloInsumoService.buscarPorId(id);

            if (articuloExistente == null) {
                return ResponseEntity.badRequest().body("No se encontró el artículo con el ID proporcionado.");
            }

            // Actualizar campos básicos
            articuloExistente.setDenominacion(articuloDto.getDenominacion());
            articuloExistente.setDescripcion(articuloDto.getDescripcion());
            articuloExistente.setCodigo(articuloDto.getCodigo());
            articuloExistente.setPrecioVenta(articuloDto.getPrecioVenta());
            articuloExistente.setPrecioCompra(articuloDto.getPrecioCompra());
            articuloExistente.setStockActual(articuloDto.getStockActual());
            articuloExistente.setStockMaximo(articuloDto.getStockMaximo());
            articuloExistente.setEsParaElaborar(articuloDto.getEsParaElaborar());
            articuloExistente.setStockMinimo(articuloDto.getStockMinimo());

            // Actualizar relaciones si se proporcionan
            if (articuloDto.getCategoriaId() != null) {
                Categoria categoria = categoriaRepository.findById(articuloDto.getCategoriaId())
                    .orElseThrow(() -> new Exception("Categoría no encontrada"));
                articuloExistente.setCategoria(categoria);
            }

            if (articuloDto.getUnidadMedidaId() != null) {
                UnidadMedida unidadMedida = unidadMedidaService.findById(articuloDto.getUnidadMedidaId());
                if (unidadMedida == null) {
                    throw new Exception("Unidad de medida no encontrada");
                }
                articuloExistente.setUnidadMedida(unidadMedida);
            }

            if (articuloDto.getSucursalId() != null) {
                Sucursal sucursal = sucursalService.traerPorId(articuloDto.getSucursalId());
                articuloExistente.setSucursal(sucursal);
            }

            // Actualizar imágenes si se proporcionan
            if (articuloDto.getImagenes() != null) {
                Set<ImagenArticulo> nuevasImagenes = new HashSet<>();
                for (String imagenBase64 : articuloDto.getImagenes()) {
                    ImagenArticulo imagen = ImagenArticulo.builder()
                            .url(imagenBase64)
                            .articulo(articuloExistente)
                            .build();
                    nuevasImagenes.add(imagen);
                }
                articuloExistente.setImagenes(nuevasImagenes);
            }

            ArticuloInsumo articuloActualizado = articuloInsumoService.actualizar(id, articuloExistente);
            return ResponseEntity.ok(articuloActualizado);

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