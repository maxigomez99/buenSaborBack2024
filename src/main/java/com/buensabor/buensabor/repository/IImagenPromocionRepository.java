package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ImagenPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImagenPromocionRepository extends JpaRepository<ImagenPromocion, Long> {
    // Método para buscar imágenes por promoción
    List<ImagenPromocion> findByPromocionId(Long promocionId);
}
