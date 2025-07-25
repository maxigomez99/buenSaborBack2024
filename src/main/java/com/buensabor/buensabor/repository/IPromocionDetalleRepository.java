package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.PromocionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPromocionDetalleRepository extends JpaRepository<PromocionDetalle, Long> {
    // Método para buscar detalles por promoción
    List<PromocionDetalle> findByPromocionId(Long promocionId);

    // Método para buscar detalles por artículo manufacturado
    List<PromocionDetalle> findByArticuloManufacturadoId(Long articuloManufacturadoId);
}
