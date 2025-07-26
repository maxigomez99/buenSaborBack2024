package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.empresa.EmpresaDto;
import com.buensabor.buensabor.entities.Empresa;
import com.buensabor.buensabor.service.IEmpresaService;
import com.buensabor.buensabor.service.funcionalidades.Funcionalidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empresa")
public class EmpresaController {
    @Autowired
    private IEmpresaService empresaService;

    @Autowired
    private Funcionalidades funcionalidades;
    // Crud
    @GetMapping("/traer-todo/eliminado/")
    public ResponseEntity<?> mostrarListaCompleta() {
        try {
            return ResponseEntity.ok(empresaService.traerTodo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/traer-todo/")
    public ResponseEntity<?> mostrarLista(){
        try {
            return ResponseEntity.ok(empresaService.traerTodoNoEliminado());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/crear-con-imagen")
    public ResponseEntity<?> crearEmpresaConImagen(@RequestBody EmpresaDto empresaDTO) {
        try {
           // Guardamos en la base de datos el string completo
            Empresa empresa = Empresa.builder()
                    .nombre(empresaDTO.getNombre())
                    .razonSocial(empresaDTO.getRazonSocial())
                    .cuil(empresaDTO.getCuil())
                    .imagen(empresaDTO.getImagen()) // Base64 completo con encabezado
                    .build();

            Empresa nuevaEmpresa = empresaService.save(empresa);

            return ResponseEntity.ok(nuevaEmpresa);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la empresa: " + e.getMessage());
        }
    }
    @PutMapping("/editar-con-imagen/{id}")
    public ResponseEntity<?> editarEmpresaConImagen(@PathVariable Long id, @RequestBody EmpresaDto empresaDTO) {
        try {
            Empresa empresaExistente = empresaService.traerPorId(id);

            if (empresaExistente == null) {
                return ResponseEntity.badRequest().body("No se encontró la empresa con el ID proporcionado.");
            }
            boolean cuilModificado = !Objects.equals(empresaDTO.getCuil(), empresaExistente.getCuil());
            if (cuilModificado) {
                Empresa empresaConMismoCuil = empresaService.buscarPorCuil(empresaDTO.getCuil());
                if (empresaConMismoCuil != null) {
                    if (!Objects.equals(empresaConMismoCuil.getId(), empresaExistente.getId())) {
                        System.out.println("ERROR: El CUIL pertenece a otra empresa.");
                        return ResponseEntity.badRequest().body("Ya existe una empresa con el mismo CUIL.");
                    } else {
                        System.out.println("CUIL pertenece a la misma empresa. ✅ OK");
                    }
                }
                empresaExistente.setCuil(empresaDTO.getCuil());
            } else {
                System.out.println("CUIL no fue modificado, se salta la validación.");
            }
            // Actualizar los demás campos
            empresaExistente.setNombre(empresaDTO.getNombre());
            empresaExistente.setRazonSocial(empresaDTO.getRazonSocial());
            empresaExistente.setImagen(empresaDTO.getImagen());

            Empresa empresaActualizada = empresaService.update(id, empresaExistente);

            System.out.println("Empresa actualizada correctamente. ✅");
            return ResponseEntity.ok(empresaActualizada);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al editar la empresa: " + e.getMessage());
        }
    }







    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(empresaService.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Empresa empresa){
        try {
            return ResponseEntity.ok(empresaService.save(empresa));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(empresaService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reactivate/{id}")
    public ResponseEntity<?> reactivar(@PathVariable Long id){
        try {
            return ResponseEntity.ok(empresaService.reactivate(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/toggle-estado")
    public ResponseEntity<?> toggleEstado(@PathVariable Long id) {
        try {
            Empresa empresaActualizada = empresaService.toggleEstado(id);
            String mensaje = empresaActualizada.isEliminado() ?
                "Empresa desactivada exitosamente" :
                "Empresa activada exitosamente";

            return ResponseEntity.ok().body(Map.of(
                "mensaje", mensaje,
                "empresa", empresaActualizada
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET - Obtener todas las empresas activas (endpoint REST estándar)
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(empresaService.findAllActive());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET - Obtener TODAS las empresas (incluyendo eliminadas)
    @GetMapping("/all")
    public ResponseEntity<?> getAllIncludingDeleted() {
        try {
            return ResponseEntity.ok(empresaService.findAllIncludingDeleted());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET - Obtener solo empresas activas (endpoint específico)
    @GetMapping("/activas")
    public ResponseEntity<?> getAllActive() {
        try {
            return ResponseEntity.ok(empresaService.findAllActive());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
