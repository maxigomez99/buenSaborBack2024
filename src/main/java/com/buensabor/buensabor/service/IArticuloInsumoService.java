package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.ArticuloInsumo;

import java.util.List;

public interface IArticuloInsumoService {
    public boolean deleteById(Long id) throws Exception;
    public List<ArticuloInsumo> mostrarLista() throws Exception;
    public ArticuloInsumo cargar(ArticuloInsumo articuloInsumo) throws Exception;
    public ArticuloInsumo buscarPorId(Long id) throws Exception;
    public ArticuloInsumo actualizar(Long id,ArticuloInsumo articuloInsumo) throws Exception;
    public boolean reactivate(Long id) throws Exception;
    public List<ArticuloInsumo> traerTodo() throws Exception;
    public ArticuloInsumo toggleEstado(Long id) throws Exception;
}

