package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.TipoPromocion;
import com.buensabor.buensabor.service.ITipoPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/all")
    public ResponseEntity<List<TipoPromocion>> getAllIncludingDeleted() {
        return ResponseEntity.ok(tipoPromocionService.findAllIncludingDeleted());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TipoPromocion>> getAllActive() {
        return ResponseEntity.ok(tipoPromocionService.findAllActive());
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
            if (tipoPromocionService.delete(id)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el tipo de promoción");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/toggle-estado")
    public ResponseEntity<?> toggleEstado(@PathVariable Long id) {
        try {
            TipoPromocion tipoActualizado = tipoPromocionService.toggleEstado(id);
            String mensaje = tipoActualizado.isEliminado() ?
                "Tipo de promoción desactivado exitosamente" :
                "Tipo de promoción activado exitosamente";

            return ResponseEntity.ok().body(Map.of(
                "mensaje", mensaje,
                "tipoPromocion", tipoActualizado
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
