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
        return unidadMedidaRepository.findAll();
    }

    @Override
    public UnidadMedida findById(Long id) {
        Optional<UnidadMedida> optionalUnidadMedida = unidadMedidaRepository.findById(id);
        return optionalUnidadMedida.orElse(null);
    }

    @Override
    public UnidadMedida save(UnidadMedida unidadMedida) {
        return unidadMedidaRepository.save(unidadMedida);
    }

    @Override
    public UnidadMedida update(Long id, UnidadMedida unidadMedida) {
        Optional<UnidadMedida> optionalUnidadMedida = unidadMedidaRepository.findById(id);

        if(optionalUnidadMedida.isPresent()) {
            UnidadMedida existingUnidadMedida = optionalUnidadMedida.get();
            existingUnidadMedida.setDenominacion(unidadMedida.getDenominacion());

            return unidadMedidaRepository.save(existingUnidadMedida);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(unidadMedidaRepository.existsById(id)) {
            // Verificar si la unidad de medida está siendo utilizada por algún artículo
            if(unidadMedidaRepository.isUsedByAnyArticulo(id)) {
                throw new RuntimeException("No se puede eliminar la unidad de medida porque está siendo utilizada por uno o más artículos");
            }

            unidadMedidaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
