package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.TipoPromocion;
import com.buensabor.buensabor.repository.ITipoPromocionRepository;
import com.buensabor.buensabor.service.ITipoPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPromocionService implements ITipoPromocionService {

    @Autowired
    private ITipoPromocionRepository tipoPromocionRepository;

    @Override
    public List<TipoPromocion> findAll() {
        return tipoPromocionRepository.findAllActive();
    }

    @Override
    public List<TipoPromocion> findAllIncludingDeleted() {
        return tipoPromocionRepository.findAllIncludingDeleted();
    }

    @Override
    public List<TipoPromocion> findAllActive() {
        return tipoPromocionRepository.findAllActive();
    }

    @Override
    public TipoPromocion findById(Long id) {
        Optional<TipoPromocion> optional = tipoPromocionRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("No se encontró el tipo de promoción con id: " + id);
    }

    @Override
    public TipoPromocion save(TipoPromocion tipoPromocion) {
        // Validar que la denominación no esté duplicada
        if (tipoPromocionRepository.existsByDenominacion(tipoPromocion.getDenominacion())) {
            throw new RuntimeException("Ya existe un tipo de promoción con la denominación: " + tipoPromocion.getDenominacion());
        }

        tipoPromocion.setEliminado(false);
        return tipoPromocionRepository.save(tipoPromocion);
    }

    @Override
    public TipoPromocion update(Long id, TipoPromocion tipoPromocion) {
        Optional<TipoPromocion> optional = tipoPromocionRepository.findById(id);
        if (optional.isPresent()) {
            TipoPromocion tipoPromocionExistente = optional.get();

            // Validar que la denominación no esté duplicada (excluyendo el registro actual)
            if (tipoPromocionRepository.existsByDenominacionAndIdNot(tipoPromocion.getDenominacion(), id)) {
                throw new RuntimeException("Ya existe otro tipo de promoción con la denominación: " + tipoPromocion.getDenominacion());
            }

            tipoPromocionExistente.setDenominacion(tipoPromocion.getDenominacion());
            tipoPromocionExistente.setDescripcion(tipoPromocion.getDescripcion());
            return tipoPromocionRepository.save(tipoPromocionExistente);
        }
        throw new RuntimeException("No se encontró el tipo de promoción con id: " + id);
    }

    @Override
    public boolean delete(Long id) {
        try {
            Optional<TipoPromocion> optional = tipoPromocionRepository.findById(id);
            if (optional.isPresent()) {
                TipoPromocion tipoPromocion = optional.get();
                // Como no hay relación directa con Promocion, siempre hacer eliminado lógico
                tipoPromocion.setEliminado(true);
                tipoPromocionRepository.save(tipoPromocion);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el tipo de promoción: " + e.getMessage());
        }
    }

    @Override
    public TipoPromocion toggleEstado(Long id) {
        Optional<TipoPromocion> optional = tipoPromocionRepository.findById(id);
        if (optional.isPresent()) {
            TipoPromocion tipoPromocion = optional.get();
            // Alternar el estado: si está eliminado (true) lo activa (false) y viceversa
            tipoPromocion.setEliminado(!tipoPromocion.isEliminado());
            return tipoPromocionRepository.save(tipoPromocion);
        }
        throw new RuntimeException("No se encontró el tipo de promoción con id: " + id);
    }
}
