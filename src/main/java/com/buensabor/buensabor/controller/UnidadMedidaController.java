package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.UnidadMedida;
import com.buensabor.buensabor.service.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/unidades-medida")
public class UnidadMedidaController {

    @Autowired
    private IUnidadMedidaService unidadMedidaService;

    @GetMapping("")
    public ResponseEntity<List<UnidadMedida>> getAll() {
        return ResponseEntity.ok(unidadMedidaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(unidadMedidaService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la unidad de medida");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UnidadMedida unidadMedida) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(unidadMedidaService.save(unidadMedida));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UnidadMedida unidadMedida) {
        try {
            return ResponseEntity.ok(unidadMedidaService.update(id, unidadMedida));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            unidadMedidaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}