package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.TipoPromocion;
import com.buensabor.buensabor.service.ITipoPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tipos-promocion")
public class TipoPromocionController {

    @Autowired
    private ITipoPromocionService tipoPromocionService;

    @GetMapping("")
    public ResponseEntity<List<TipoPromocion>> getAll() {
        return ResponseEntity.ok(tipoPromocionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tipoPromocionService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el tipo de promoción");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody TipoPromocion tipoPromocion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoPromocionService.save(tipoPromocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TipoPromocion tipoPromocion) {
        try {
            return ResponseEntity.ok(tipoPromocionService.update(id, tipoPromocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            tipoPromocionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
