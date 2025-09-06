package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.TipoPromocion;

import java.util.List;

public interface ITipoPromocionService {

    List<TipoPromocion> findAll();
    List<TipoPromocion> findAllIncludingDeleted();
    List<TipoPromocion> findAllActive();
    TipoPromocion findById(Long id);
    TipoPromocion save(TipoPromocion tipoPromocion);
    TipoPromocion update(Long id, TipoPromocion tipoPromocion);
    boolean delete(Long id);

    TipoPromocion toggleEstado(Long id);
}
