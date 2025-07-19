package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.entities.ArticuloInsumo;
import com.buensabor.buensabor.service.IArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/articulos-insumo")
public class ArticuloInsumoController extends ArticuloController<ArticuloInsumo, IArticuloInsumoService> {

    @Autowired
    public ArticuloInsumoController(IArticuloInsumoService service) {
        super(service);
    }

    @GetMapping("/para-elaborar/{esParaElaborar}")
    public ResponseEntity<?> getByEsParaElaborar(@PathVariable Boolean esParaElaborar) {
        try {
            return ResponseEntity.ok(service.findByEsParaElaborar(esParaElaborar));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
