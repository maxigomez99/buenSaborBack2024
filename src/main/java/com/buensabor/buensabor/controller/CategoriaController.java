package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;





}
