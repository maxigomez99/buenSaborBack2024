package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.PromocionDetalle;

import java.util.List;

public interface IPromocionDetalleService {

    List<PromocionDetalle> findAll();

    PromocionDetalle findById(Long id);

    PromocionDetalle save(PromocionDetalle promocionDetalle);

    PromocionDetalle update(Long id, PromocionDetalle promocionDetalle);

    boolean delete(Long id);

    List<PromocionDetalle> findByPromocionId(Long promocionId);

    List<PromocionDetalle> findByArticuloManufacturadoId(Long articuloManufacturadoId);
}
