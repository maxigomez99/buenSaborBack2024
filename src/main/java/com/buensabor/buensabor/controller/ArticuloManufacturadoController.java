package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.ArticuloManufacturado;
import com.buensabor.buensabor.service.IArticuloManufacturadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/articulos-manufacturados")
public class ArticuloManufacturadoController extends ArticuloController<ArticuloManufacturado, IArticuloManufacturadoService> {

    @Autowired
    public ArticuloManufacturadoController(IArticuloManufacturadoService service) {
        super(service);
    }

    @GetMapping("/tiempo-menor/{minutos}")
    public ResponseEntity<?> getByTiempoEstimadoMinutosLessThan(@PathVariable Integer minutos) {
        try {
            return ResponseEntity.ok(service.findByTiempoEstimadoMinutosLessThan(minutos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}