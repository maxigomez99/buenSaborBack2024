package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.UnidadMedida;
import com.buensabor.buensabor.repository.IUnidadMedidaRepository;
import com.buensabor.buensabor.service.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadMedidaService implements IUnidadMedidaService {

    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    @Override
    public List<UnidadMedida> findAll() {
        return unidadMedidaRepository.findAllActive();
    }

    @Override
    public List<UnidadMedida> findAllIncludingDeleted() {
        return unidadMedidaRepository.findAllIncludingDeleted();
    }

    @Override
    public List<UnidadMedida> findAllActive() {
        return unidadMedidaRepository.findAllActive();
    }

    @Override
    public UnidadMedida findById(Long id) {
        Optional<UnidadMedida> optional = unidadMedidaRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("No se encontró la unidad de medida con id: " + id);
    }

    @Override
    public UnidadMedida save(UnidadMedida unidadMedida) {
        // Validar que la denominación no esté duplicada
        if (unidadMedidaRepository.existsByDenominacion(unidadMedida.getDenominacion())) {
            throw new RuntimeException("Ya existe una unidad de medida con la denominación: " + unidadMedida.getDenominacion());
        }

        // Validar que la abreviatura no esté duplicada
        if (unidadMedidaRepository.existsByAbreviatura(unidadMedida.getAbreviatura())) {
            throw new RuntimeException("Ya existe una unidad de medida con la abreviatura: " + unidadMedida.getAbreviatura());
        }

        unidadMedida.setEliminado(false);
        return unidadMedidaRepository.save(unidadMedida);
    }

    @Override
    public UnidadMedida update(Long id, UnidadMedida unidadMedida) {
        Optional<UnidadMedida> optional = unidadMedidaRepository.findById(id);
        if (optional.isPresent()) {
            UnidadMedida unidadMedidaExistente = optional.get();

            // Validar que la denominación no esté duplicada (excluyendo el registro actual)
            if (unidadMedidaRepository.existsByDenominacionAndIdNot(unidadMedida.getDenominacion(), id)) {
                throw new RuntimeException("Ya existe otra unidad de medida con la denominación: " + unidadMedida.getDenominacion());
            }

            // Validar que la abreviatura no esté duplicada (excluyendo el registro actual)
            if (unidadMedidaRepository.existsByAbreviaturaAndIdNot(unidadMedida.getAbreviatura(), id)) {
                throw new RuntimeException("Ya existe otra unidad de medida con la abreviatura: " + unidadMedida.getAbreviatura());
            }

            unidadMedidaExistente.setDenominacion(unidadMedida.getDenominacion());
            unidadMedidaExistente.setAbreviatura(unidadMedida.getAbreviatura());
            return unidadMedidaRepository.save(unidadMedidaExistente);
        }
        throw new RuntimeException("No se encontró la unidad de medida con id: " + id);
    }

    @Override
    public boolean delete(Long id) {
        try {
            Optional<UnidadMedida> optional = unidadMedidaRepository.findById(id);
            if (optional.isPresent()) {
                UnidadMedida unidadMedida = optional.get();

                // Verificar si está siendo utilizada por algún artículo
                if (unidadMedidaRepository.isUsedByAnyArticulo(id)) {
                    // Solo marcar como eliminado si está siendo utilizada
                    unidadMedida.setEliminado(true);
                    unidadMedidaRepository.save(unidadMedida);
                } else {
                    // Eliminar físicamente si no está siendo utilizada
                    unidadMedidaRepository.deleteById(id);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la unidad de medida: " + e.getMessage());
        }
    }

    @Override
    public UnidadMedida toggleEstado(Long id) {
        Optional<UnidadMedida> optional = unidadMedidaRepository.findById(id);
        if (optional.isPresent()) {
            UnidadMedida unidadMedida = optional.get();
            // Alternar el estado: si está eliminado (true) lo activa (false) y viceversa
            unidadMedida.setEliminado(!unidadMedida.isEliminado());
            return unidadMedidaRepository.save(unidadMedida);
        }
        throw new RuntimeException("No se encontró la unidad de medida con id: " + id);
    }
}
