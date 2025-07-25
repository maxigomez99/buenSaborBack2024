package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Categoria;

import java.util.List;

public interface ICategoriaService {
    List<Categoria> findAll();

    Categoria findById(Long id);

    Categoria save(Categoria categoria);

    Categoria update(Long id, Categoria categoria);

    boolean delete(Long id);

    List<Categoria> findByEmpresaId(Long empresaId);
}
