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
        return tipoPromocionRepository.findAll();
    }

    @Override
    public TipoPromocion findById(Long id) {
        Optional<TipoPromocion> optionalTipoPromocion = tipoPromocionRepository.findById(id);
        return optionalTipoPromocion.orElse(null);
    }

    @Override
    public TipoPromocion save(TipoPromocion tipoPromocion) {
        return tipoPromocionRepository.save(tipoPromocion);
    }

    @Override
    public TipoPromocion update(Long id, TipoPromocion tipoPromocion) {
        Optional<TipoPromocion> optionalTipoPromocion = tipoPromocionRepository.findById(id);

        if(optionalTipoPromocion.isPresent()) {
            TipoPromocion existingTipo = optionalTipoPromocion.get();
            existingTipo.setDenominacion(tipoPromocion.getDenominacion());
            existingTipo.setDescripcion(tipoPromocion.getDescripcion());

            return tipoPromocionRepository.save(existingTipo);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(tipoPromocionRepository.existsById(id)) {
            tipoPromocionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
