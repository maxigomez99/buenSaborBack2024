package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.categoria.CategoriaDto;
import com.buensabor.buensabor.dto.categoria.CategoriaEmpresaDto;

import com.buensabor.buensabor.dto.categoria.SubCategoriaConEmpresaDto;
import com.buensabor.buensabor.entities.Categoria;
import com.buensabor.buensabor.errores.ApiError;
import com.buensabor.buensabor.service.ICategoriaService;
import com.buensabor.buensabor.service.impl.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/")
    public ResponseEntity<?> lista() {
        try {
            return ResponseEntity.ok().body(categoriaService.lista());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(categoriaService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.cargar(categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.Actualizar(id, categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(categoriaService.eliminar(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //agrega una subcategoria a una categoria
    @PostMapping("/agregar/subcategoria/{id}")
    public ResponseEntity<?> crearSubCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.agregarSubCategoria(id, categoria));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }


    @PostMapping("/agregar/articulo")
    public ResponseEntity<?> agregarArticulo(@RequestParam("idCategoria") Long idCategoria, @RequestParam("idArticulo") Long idArticulo) {
        try {
            return ResponseEntity.ok().body(categoriaService.agregarArticulo(idCategoria, idArticulo));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //obtiene todas las categorias con subcategorias
    @GetMapping("/categoriasConSubcategorias/")
    public ResponseEntity<?> obtenerCategoriasConSubCategorias() {
        try {
            return ResponseEntity.ok().body(categoriaService.obtenerCategoriasConSubCategorias());
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @DeleteMapping("/eliminar/articulo/{idSubCategoria}/{idArticulo}")
    public ResponseEntity<?> eliminarArticuloDeSubCategoria(@PathVariable Long idSubCategoria, @PathVariable Long idArticulo) {
        try {
            return ResponseEntity.ok().body(categoriaService.eliminarArticuloDeSubCategoria(idSubCategoria, idArticulo));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/actualizar/subcategoria/{idSubCategoria}")
    public ResponseEntity<?> actualizarSubCategoria(@PathVariable Long idSubCategoria, @RequestBody Categoria nuevaSubCategoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.actualizarSubCategoria(idSubCategoria, nuevaSubCategoria));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @DeleteMapping("/eliminar/subcategoria")
    public ResponseEntity<?> eliminarSubCategoria(@RequestParam("idCategoria") Long idCategoria, @RequestParam("idSubCategoria") Long idSubCategoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.eliminarSubCategoria(idCategoria, idSubCategoria));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/reactivate/{id}")
    public ResponseEntity<?> reactivate(@PathVariable Long id) {
        try {
            categoriaService.reactivate(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/traer-todo/")
    public ResponseEntity<?> traerTodo() {
        try {
            return ResponseEntity.ok(categoriaService.traerTodo());
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //obtiene todas las subcategorias
    @GetMapping("/subcategorias/{id}")
    public ResponseEntity<?> obtenerSubCategorias(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(categoriaService.obtenerSubCategorias(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/categoriasPadre/{sucursalId}")
    public ResponseEntity<?> obtenerCategoriasPadre(@PathVariable Long sucursalId) {
        try {
            return ResponseEntity.ok().body(categoriaService.traerCategoriaPadre(sucursalId));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/{id}/tieneSubCategorias")
    public ResponseEntity<?> tieneSubCategorias(@PathVariable Long id) {
        try {
            boolean tieneSubCategorias = categoriaService.tieneSubCategorias(id);
            return ResponseEntity.ok().body(tieneSubCategorias);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }


    //---------------------Categoria por Empresa------------------------------------------------------------
    @Autowired
    private CategoriaService catService;

    @PostMapping("/porEmpresa")
    public ResponseEntity<?> crearCategoriaporEmpresa(@RequestBody CategoriaEmpresaDto categoriaDto) throws Exception {
        try {
            Categoria categoriaGuardada = catService.crearCategoriaporEmpresa(categoriaDto);
            return ResponseEntity.ok(categoriaGuardada);

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/{id}/denominacion")
    public ResponseEntity<?> actualizarDenominacion(@PathVariable Long id, @RequestBody CategoriaDto dto) throws Exception {
        try {
            CategoriaDto categoriaActualizada = catService.actualizarDenominacion(id, dto.getDenominacion(), dto.getUrlIcono());
            return ResponseEntity.ok(categoriaActualizada);

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/{id}/eliminado")
    public ResponseEntity<Categoria> cambiarEstadoEliminado(@PathVariable Long id) {
        Categoria categoriaActualizada = catService.cambiarEstadoEliminado(id);
        return ResponseEntity.ok(categoriaActualizada);
    }


    @PostMapping("/subcategoriaConEmpresa")
    public ResponseEntity<?> crearSubCategoriaConEmpresa(@RequestBody SubCategoriaConEmpresaDto subCategoriaDto) throws IOException {

        return ResponseEntity.ok(catService.crearSubCategoriaConEmpresa(subCategoriaDto));
    }

    //------------------
    @GetMapping("/porEmpresa/{idEmpresa}")
    public ResponseEntity<?> obtenerCategoriasPorIdEmpresa(@PathVariable Long idEmpresa) {
        try {
            return ResponseEntity.ok(catService.traerTodo2(idEmpresa));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @CrossOrigin(origins = "https://ecommerce-buen-sabor.vercel.app")
    @GetMapping("/traer-categoria-padre")
    public ResponseEntity<?> traerCategoriaPadre() {
        try {
            return ResponseEntity.ok(catService.traerCategoriasPadres());
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //---------------------Asociación de Sucursales------------------------------------------------------------
    @PostMapping("/agregarSucursalACategoria/{categoriaId}/{sucursalId}")
    public ResponseEntity<?> agregarSucursalACategoria(@PathVariable Long categoriaId, @PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(catService.agregarSucursalACategoria(categoriaId, sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/desasociarSucursalDeCategoria/{categoriaId}/{sucursalId}")
    public ResponseEntity<?> desasociarSucursalDeCategoria(@PathVariable Long categoriaId, @PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(catService.desasociarSucursalDeCategoria(categoriaId, sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/porSucursal/{sucursalId}")
    public ResponseEntity<?> obtenerCategoriasPorSucursal(@PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(catService.obtenerCategoriasPorSucursal(sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/no-asociadas/{sucursalId}/{empresaId}")
    public ResponseEntity<?> obtenerCategoriasNoAsociadasASucursal(@PathVariable Long sucursalId, @PathVariable Long empresaId) {
        try {
            return ResponseEntity.ok().body(catService.traerCategoriasNoAsociadasASucursal(sucursalId, empresaId));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

}
