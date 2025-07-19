package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.PromocionDetalle;
import com.buensabor.buensabor.repository.IPromocionDetalleRepository;
import com.buensabor.buensabor.service.IPromocionDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromocionDetalleService implements IPromocionDetalleService {

    @Autowired
    private IPromocionDetalleRepository promocionDetalleRepository;

    @Override
    public List<PromocionDetalle> findAll() {
        return promocionDetalleRepository.findAll();
    }

    @Override
    public PromocionDetalle findById(Long id) {
        Optional<PromocionDetalle> optionalPromocionDetalle = promocionDetalleRepository.findById(id);
        return optionalPromocionDetalle.orElse(null);
    }

    @Override
    public PromocionDetalle save(PromocionDetalle promocionDetalle) {
        return promocionDetalleRepository.save(promocionDetalle);
    }

    @Override
    public PromocionDetalle update(Long id, PromocionDetalle promocionDetalle) {
        Optional<PromocionDetalle> optionalPromocionDetalle = promocionDetalleRepository.findById(id);

        if(optionalPromocionDetalle.isPresent()) {
            PromocionDetalle existingDetalle = optionalPromocionDetalle.get();
            existingDetalle.setCantidad(promocionDetalle.getCantidad());
            existingDetalle.setPromocion(promocionDetalle.getPromocion());
            existingDetalle.setArticuloManufacturado(promocionDetalle.getArticuloManufacturado());

            return promocionDetalleRepository.save(existingDetalle);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(promocionDetalleRepository.existsById(id)) {
            promocionDetalleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<PromocionDetalle> findByPromocionId(Long promocionId) {
        return promocionDetalleRepository.findByPromocionId(promocionId);
    }

    @Override
    public List<PromocionDetalle> findByArticuloManufacturadoId(Long articuloManufacturadoId) {
        return promocionDetalleRepository.findByArticuloManufacturadoId(articuloManufacturadoId);
    }
}
