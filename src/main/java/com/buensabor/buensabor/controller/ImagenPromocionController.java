package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.ImagenPromocion;
import com.buensabor.buensabor.service.IImagenPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/imagenes-promocion")
public class ImagenPromocionController {

    @Autowired
    private IImagenPromocionService imagenPromocionService;

    @GetMapping("")
    public ResponseEntity<List<ImagenPromocion>> getAll() {
        return ResponseEntity.ok(imagenPromocionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(imagenPromocionService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la imagen de promoción");
        }
    }

    @GetMapping("/promocion/{promocionId}")
    public ResponseEntity<?> getByPromocionId(@PathVariable Long promocionId) {
        try {
            return ResponseEntity.ok(imagenPromocionService.findByPromocionId(promocionId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ImagenPromocion imagenPromocion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(imagenPromocionService.save(imagenPromocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ImagenPromocion imagenPromocion) {
        try {
            return ResponseEntity.ok(imagenPromocionService.update(id, imagenPromocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            imagenPromocionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}