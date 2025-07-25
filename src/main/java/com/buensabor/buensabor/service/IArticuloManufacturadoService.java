package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.ArticuloManufacturado;

import java.util.List;

public interface IArticuloManufacturadoService extends IArticuloService<ArticuloManufacturado> {
    // Métodos específicos para ArticuloManufacturado (si se necesitan)
    List<ArticuloManufacturado> findByTiempoEstimadoMinutosLessThan(Integer minutos) throws Exception;
}
