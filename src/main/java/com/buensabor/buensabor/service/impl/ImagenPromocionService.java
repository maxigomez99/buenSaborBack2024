package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.ImagenPromocion;
import com.buensabor.buensabor.repository.IImagenPromocionRepository;
import com.buensabor.buensabor.service.IImagenPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenPromocionService implements IImagenPromocionService {

    @Autowired
    private IImagenPromocionRepository imagenPromocionRepository;

    @Override
    public List<ImagenPromocion> findAll() {
        return imagenPromocionRepository.findAll();
    }

    @Override
    public ImagenPromocion findById(Long id) {
        Optional<ImagenPromocion> optionalImagenPromocion = imagenPromocionRepository.findById(id);
        return optionalImagenPromocion.orElse(null);
    }

    @Override
    public ImagenPromocion save(ImagenPromocion imagenPromocion) {
        return imagenPromocionRepository.save(imagenPromocion);
    }

    @Override
    public ImagenPromocion update(Long id, ImagenPromocion imagenPromocion) {
        Optional<ImagenPromocion> optionalImagenPromocion = imagenPromocionRepository.findById(id);

        if(optionalImagenPromocion.isPresent()) {
            ImagenPromocion existingImagen = optionalImagenPromocion.get();
            existingImagen.setDenominacion(imagenPromocion.getDenominacion());
            existingImagen.setPromocion(imagenPromocion.getPromocion());

            return imagenPromocionRepository.save(existingImagen);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(imagenPromocionRepository.existsById(id)) {
            imagenPromocionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ImagenPromocion> findByPromocionId(Long promocionId) {
        return imagenPromocionRepository.findByPromocionId(promocionId);
    }
}
