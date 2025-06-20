package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.empresa.EmpresaDto;
import com.buensabor.buensabor.entities.Empresa;
import com.buensabor.buensabor.service.IEmpresaService;
import com.buensabor.buensabor.service.funcionalidades.Funcionalidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
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
            System.out.println("Nombre recibido: " + empresaDTO.getNombre());
            System.out.println("Razón Social recibida: " + empresaDTO.getRazonSocial());
            System.out.println("CUIL recibido: " + empresaDTO.getCuil());
            System.out.println("Imagen Base64 recibida: " + empresaDTO.getImagen());





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

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody Empresa empresa){
        try {
            return ResponseEntity.ok(empresaService.update(id, empresa));
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

}
