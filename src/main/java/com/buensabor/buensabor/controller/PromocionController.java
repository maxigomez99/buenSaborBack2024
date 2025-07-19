package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.Promocion;
import com.buensabor.buensabor.service.IPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/promociones")
public class PromocionController {

    @Autowired
    private IPromocionService promocionService;

    @GetMapping("")
    public ResponseEntity<List<Promocion>> getAll() {
        return ResponseEntity.ok(promocionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(promocionService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la promoción");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Promocion promocion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.save(promocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Promocion promocion) {
        try {
            return ResponseEntity.ok(promocionService.update(id, promocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            promocionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}