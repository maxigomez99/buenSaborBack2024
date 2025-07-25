package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Articulo;

import java.util.List;

/**
 * Interfaz base para los servicios de artículos
 * @param <E> Tipo de artículo (ArticuloInsumo o ArticuloManufacturado)
 */
public interface IArticuloService<E extends Articulo> {

    List<E> findAll() throws Exception;

    E findById(Long id) throws Exception;

    List<E> findByCategoriaId(Long categoriaId) throws Exception;

    E save(E entity) throws Exception;

    E update(Long id, E entity) throws Exception;

    boolean delete(Long id) throws Exception;
}
