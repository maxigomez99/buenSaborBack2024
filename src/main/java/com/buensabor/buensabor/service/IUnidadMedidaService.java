package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.UnidadMedida;

import java.util.List;

public interface IUnidadMedidaService {

    List<UnidadMedida> findAll();
    List<UnidadMedida> findAllIncludingDeleted();
    List<UnidadMedida> findAllActive();
    UnidadMedida findById(Long id);
    UnidadMedida save(UnidadMedida unidadMedida);
    UnidadMedida update(Long id, UnidadMedida unidadMedida);
    boolean delete(Long id);

    UnidadMedida toggleEstado(Long id);
}
