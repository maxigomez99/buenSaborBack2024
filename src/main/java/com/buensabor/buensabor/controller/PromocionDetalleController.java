package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.PromocionDetalle;
import com.buensabor.buensabor.service.IPromocionDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/promocion-detalles")
public class PromocionDetalleController {

    @Autowired
    private IPromocionDetalleService promocionDetalleService;

    @GetMapping("")
    public ResponseEntity<List<PromocionDetalle>> getAll() {
        return ResponseEntity.ok(promocionDetalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(promocionDetalleService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el detalle de promoción");
        }
    }

    @GetMapping("/promocion/{promocionId}")
    public ResponseEntity<?> getByPromocionId(@PathVariable Long promocionId) {
        try {
            return ResponseEntity.ok(promocionDetalleService.findByPromocionId(promocionId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody PromocionDetalle promocionDetalle) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(promocionDetalleService.save(promocionDetalle));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PromocionDetalle promocionDetalle) {
        try {
            return ResponseEntity.ok(promocionDetalleService.update(id, promocionDetalle));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            promocionDetalleService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
