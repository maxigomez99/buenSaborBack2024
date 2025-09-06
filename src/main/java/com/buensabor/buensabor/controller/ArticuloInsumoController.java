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

    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<?> guardar(@RequestBody ArticuloInsumo articuloInsumo) {
        try {
            ArticuloInsumo insumo = articuloInsumoService.cargar(articuloInsumo);

            return ResponseEntity.ok(articuloInsumoService.buscarPorId(insumo.getId()));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ArticuloInsumo articuloInsumo) {
        try {
            return ResponseEntity.ok(articuloInsumoService.actualizar(id, articuloInsumo));
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

    @GetMapping("/taer-todo/")
    public ResponseEntity<?> traerTodo() {
        try {
            return ResponseEntity.ok(articuloInsumoService.traerTodo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

}