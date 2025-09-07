package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.PromocionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPromocionDetalleRepository extends JpaRepository<PromocionDetalle, Long> {
    List<PromocionDetalle> findByPromocion_Id(Long promocionId);

    List<PromocionDetalle> findByPromocionId(Long promocionId);

}
