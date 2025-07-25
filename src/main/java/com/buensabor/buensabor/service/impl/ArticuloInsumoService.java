package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.ArticuloInsumo;
import com.buensabor.buensabor.repository.IArticuloInsumoRepository;
import com.buensabor.buensabor.service.IArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloInsumoService implements IArticuloInsumoService {

    @Autowired
    private IArticuloInsumoRepository articuloInsumoRepository;

    @Override
    public List<ArticuloInsumo> findAll() {
        return articuloInsumoRepository.findAll();
    }

    @Override
    public ArticuloInsumo findById(Long id) throws Exception {
        Optional<ArticuloInsumo> optionalArticuloInsumo = articuloInsumoRepository.findById(id);
        if (optionalArticuloInsumo.isPresent()) {
            return optionalArticuloInsumo.get();
        } else {
            throw new Exception("No existe el artículo insumo con id: " + id);
        }
    }

    @Override
    public ArticuloInsumo save(ArticuloInsumo articuloInsumo) throws Exception {
        try {
            return articuloInsumoRepository.save(articuloInsumo);
        } catch (Exception e) {
            throw new Exception("Error al guardar el artículo insumo: " + e.getMessage());
        }
    }

    @Override
    public ArticuloInsumo update(Long id, ArticuloInsumo articuloInsumo) throws Exception {
        try {
            Optional<ArticuloInsumo> optionalArticuloInsumo = articuloInsumoRepository.findById(id);
            if (optionalArticuloInsumo.isPresent()) {
                return articuloInsumoRepository.save(articuloInsumo);
            } else {
                throw new Exception("No existe el artículo insumo con id: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar el artículo insumo: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (articuloInsumoRepository.existsById(id)) {
                articuloInsumoRepository.deleteById(id);
                return true;
            } else {
                throw new Exception("No existe el artículo insumo con id: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar el artículo insumo: " + e.getMessage());
        }
    }

    @Override
    public List<ArticuloInsumo> findByCategoriaId(Long categoriaId) throws Exception {
        try {
            // Como el método findByCategoriaId no existe en el repositorio, filtramos manualmente
            return articuloInsumoRepository.findAll().stream()
                    .filter(articulo -> articulo.getCategoria() != null &&
                            articulo.getCategoria().getId().equals(categoriaId))
                    .toList();
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos insumo por categoría: " + e.getMessage());
        }
    }

    @Override
    public List<ArticuloInsumo> findByEsParaElaborar(Boolean esParaElaborar) throws Exception {
        try {
            return articuloInsumoRepository.findByEsParaElaborar(esParaElaborar);
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos insumo por esParaElaborar: " + e.getMessage());
        }
    }
}
