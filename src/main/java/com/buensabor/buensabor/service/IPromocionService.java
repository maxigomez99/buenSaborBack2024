package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Promocion;

import java.util.List;

public interface IPromocionService {

    List<Promocion> findAll();

    Promocion findById(Long id);

    Promocion save(Promocion promocion);

    Promocion update(Long id, Promocion promocion);

    boolean delete(Long id);

    List<Promocion> findByEmpresaId(Long empresaId);
}
