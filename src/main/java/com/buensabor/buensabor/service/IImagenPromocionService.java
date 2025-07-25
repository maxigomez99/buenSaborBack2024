package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.ImagenPromocion;

import java.util.List;

public interface IImagenPromocionService {

    List<ImagenPromocion> findAll();

    ImagenPromocion findById(Long id);

    ImagenPromocion save(ImagenPromocion imagenPromocion);

    ImagenPromocion update(Long id, ImagenPromocion imagenPromocion);

    boolean delete(Long id);

    List<ImagenPromocion> findByPromocionId(Long promocionId);
}
