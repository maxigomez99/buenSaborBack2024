package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.Promocion;
import com.buensabor.buensabor.repository.IPromocionRepository;
import com.buensabor.buensabor.service.IPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromocionService implements IPromocionService {

    @Autowired
    private IPromocionRepository promocionRepository;

    @Override
    public List<Promocion> findAll() {
        return promocionRepository.findAll();
    }

    @Override
    public Promocion findById(Long id) {
        Optional<Promocion> optionalPromocion = promocionRepository.findById(id);
        return optionalPromocion.orElse(null);
    }

    @Override
    public Promocion save(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    @Override
    public Promocion update(Long id, Promocion promocion) {
        Optional<Promocion> optionalPromocion = promocionRepository.findById(id);

        if(optionalPromocion.isPresent()) {
            Promocion existingPromocion = optionalPromocion.get();

            existingPromocion.setDenominacion(promocion.getDenominacion());
            existingPromocion.setFechaDesde(promocion.getFechaDesde());
            existingPromocion.setFechaHasta(promocion.getFechaHasta());
            existingPromocion.setHoraDesde(promocion.getHoraDesde());
            existingPromocion.setHoraHasta(promocion.getHoraHasta());
            existingPromocion.setDescDescuento(promocion.getDescDescuento());
            existingPromocion.setPrecioPromo(promocion.getPrecioPromo());
            existingPromocion.setTipoPromocion(promocion.getTipoPromocion());
            existingPromocion.setEmpresa(promocion.getEmpresa());

            // No actualizamos directamente las listas para evitar problemas con los detalles
            // y las imágenes asociadas

            return promocionRepository.save(existingPromocion);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(promocionRepository.existsById(id)) {
            promocionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Promocion> findByEmpresaId(Long empresaId) {
        return promocionRepository.findByEmpresaId(empresaId);
    }
}
