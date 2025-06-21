package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.Localidad;
import com.buensabor.buensabor.entities.Provincia;
import com.buensabor.buensabor.service.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provincia")
public class ProvinciaController {

    @Autowired
    private IProvinciaService provinciaService;

    //region CRUD Basico
    @GetMapping("/pais/{paisId}")
    public List<Provincia> getProvinciasByPais(@PathVariable Long paisId) {
        return provinciaService.getProvinciaByPaisId(paisId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(provinciaService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
