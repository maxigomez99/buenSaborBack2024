package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.ArticuloInsumo;

import java.util.List;

public interface IArticuloInsumoService extends IArticuloService<ArticuloInsumo> {
    // Métodos específicos para ArticuloInsumo
    List<ArticuloInsumo> findByEsParaElaborar(Boolean esParaElaborar) throws Exception;
}
