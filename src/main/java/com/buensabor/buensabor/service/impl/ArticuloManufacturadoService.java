package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.ArticuloManufacturado;
import com.buensabor.buensabor.repository.IArticuloManufacturadoRepository;
import com.buensabor.buensabor.service.IArticuloManufacturadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticuloManufacturadoService implements IArticuloManufacturadoService {

    @Autowired
    private IArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Override
    public List<ArticuloManufacturado> findAll() {
        return articuloManufacturadoRepository.findAll();
    }

    @Override
    public ArticuloManufacturado findById(Long id) {
        Optional<ArticuloManufacturado> optionalArticuloManufacturado = articuloManufacturadoRepository.findById(id);
        return optionalArticuloManufacturado.orElse(null);
    }

    @Override
    public ArticuloManufacturado save(ArticuloManufacturado articuloManufacturado) {
        return articuloManufacturadoRepository.save(articuloManufacturado);
    }

    @Override
    public ArticuloManufacturado update(Long id, ArticuloManufacturado articuloManufacturado) {
        Optional<ArticuloManufacturado> optionalArticuloManufacturado = articuloManufacturadoRepository.findById(id);

        if(optionalArticuloManufacturado.isPresent()) {
            ArticuloManufacturado existingArticulo = optionalArticuloManufacturado.get();
            existingArticulo.setDenominacion(articuloManufacturado.getDenominacion());
            existingArticulo.setDescripcion(articuloManufacturado.getDescripcion());
            existingArticulo.setTiempoEstimadoMinutos(articuloManufacturado.getTiempoEstimadoMinutos());
            existingArticulo.setPreparacion(articuloManufacturado.getPreparacion());
            existingArticulo.setPrecioVenta(articuloManufacturado.getPrecioVenta());
            existingArticulo.setCategoria(articuloManufacturado.getCategoria());

            // No actualizamos las listas de detalles, imágenes, etc. directamente aquí
            // para eso necesitarías lógica adicional más específica

            return articuloManufacturadoRepository.save(existingArticulo);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(articuloManufacturadoRepository.existsById(id)) {
            articuloManufacturadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ArticuloManufacturado> findByCategoriaId(Long categoriaId) throws Exception {
        try {
            return articuloManufacturadoRepository.findAll().stream()
                .filter(articulo -> articulo.getCategoria() != null &&
                        articulo.getCategoria().getId().equals(categoriaId))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos manufacturados por categoría: " + e.getMessage());
        }
    }

    @Override
    public List<ArticuloManufacturado> findByTiempoEstimadoMinutosLessThan(Integer minutos) throws Exception {
        try {
            return articuloManufacturadoRepository.findAll().stream()
                .filter(articulo -> articulo.getTiempoEstimadoMinutos() != null &&
                        articulo.getTiempoEstimadoMinutos() < minutos)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos manufacturados por tiempo estimado: " + e.getMessage());
        }
    }
}
