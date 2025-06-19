package com.buensabor.buensabor.controller;
import com.buensabor.buensabor.entities.Localidad;
import com.buensabor.buensabor.service.ILocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/localidad")

public class LocalidadController {
    @Autowired
    private ILocalidadService localidadService;

    //Crud

    @GetMapping("/provincia/{provinciaId}")
    public List<Localidad> getLocalidadesByProvincia(@PathVariable Long provinciaId) {
        return localidadService.getLocalidadesByProvinciaId(provinciaId);
    }
    @GetMapping("/traer-todo/")
    public ResponseEntity<?> mostrarLista() {
        try {
            return ResponseEntity.ok(localidadService.buscarTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(localidadService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Localidad localidad){
        try {
            return ResponseEntity.ok(localidadService.guardar(localidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(localidadService.eliminar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reactivate/{id}")
    public ResponseEntity<?> reactivar(@PathVariable Long id){
        try {
            return ResponseEntity.ok(localidadService.reactivar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
