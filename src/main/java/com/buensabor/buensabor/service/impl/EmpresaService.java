package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.service.IEmpresaService;
import org.springframework.stereotype.Service;
import com.buensabor.buensabor.entities.Empresa;
import com.buensabor.buensabor.repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.buensabor.buensabor.repository.ISucursalRepository;
import com.buensabor.buensabor.service.funcionalidades.Funcionalidades;
import com.buensabor.buensabor.entities.Sucursal;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository empresaRepository;
    @Autowired
    private ISucursalRepository sucursalRepository;
    @Autowired
    private Funcionalidades funcionalidades;
    @Override
    public Empresa save(Empresa empresa) throws Exception {
        try {
            if (empresaRepository.existsByCuil(empresa.getCuil())) {
                throw new Exception("Ya existe una empresa con el CUIL proporcionado");
            }
            if (empresaRepository.existsByNombre(empresa.getNombre())) {
                throw new Exception("Ya existe una empresa con el nombre proporcionado");
            }

            // Ya no se guarda archivo, se mantiene el base64 como está
            return empresaRepository.save(empresa);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Empresa update(Long id, Empresa empresa) throws Exception {
        try {
            Empresa existingEmpresa = empresaRepository.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró la empresa"));

            // Validar nombre
            Empresa otraConMismoNombre = empresaRepository.findByNombre(empresa.getNombre());
            if (otraConMismoNombre != null && !otraConMismoNombre.getId().equals(id)) {
                throw new Exception("Ya existe una empresa con el mismo nombre");
            }

            // Ya validaste el CUIL en el controller, no lo repitas acá

            // Actualizar campos
            existingEmpresa.setNombre(empresa.getNombre());
            existingEmpresa.setRazonSocial(empresa.getRazonSocial());
            existingEmpresa.setCuil(empresa.getCuil());

            // Actualizar imagen solo si se recibe una nueva (en base64)
            if (empresa.getImagen() != null && !empresa.getImagen().isEmpty()) {
                existingEmpresa.setImagen(empresa.getImagen());
            }

            return empresaRepository.save(existingEmpresa);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public boolean delete(Long id) throws Exception {
        try {
            Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la empresa con el id proporcionado"));

            // Verificar si la empresa tiene alguna sucursal asociada activa
            List<Sucursal> sucursalesActivas = sucursalRepository.findByEmpresaIdAndEliminadoFalse(id);
            if (!sucursalesActivas.isEmpty()) {
                throw new Exception("No se puede modificar el estado de la empresa porque tiene sucursales asociadas activas");
            }

            // Cambiar el estado de eliminado a no eliminado y viceversa
            empresa.setEliminado(!empresa.isEliminado());
            empresaRepository.save(empresa);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            if (empresaRepository.existsById(id)) {
                Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la empresa con el id proporcionado"));
                if (empresa.isEliminado()) {
                    empresa.setEliminado(false);
                    empresaRepository.save(empresa);
                    return true;
                } else {
                    throw new Exception("La Empresa con el id proporcionado no está eliminada");
                }
            } else {
                throw new Exception("No existe la Empresa con el id proporcionado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Empresa> traerTodo() throws Exception {
        try {
            return empresaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public List<Empresa> traerTodoNoEliminado() throws Exception {
        try {
            return empresaRepository.findByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Empresa traerPorId(Long id) throws Exception {
        try {
            return empresaRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public Empresa buscarPorCuil(Long cuil) {
        return empresaRepository.findByCuil(cuil);
    }

}
