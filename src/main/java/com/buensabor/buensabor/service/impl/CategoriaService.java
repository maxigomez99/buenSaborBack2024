package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.entities.Categoria;
import com.buensabor.buensabor.repository.ICategoriaRepository;
import com.buensabor.buensabor.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        return optionalCategoria.orElse(null);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Long id, Categoria categoria) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

        if(optionalCategoria.isPresent()) {
            Categoria existingCategoria = optionalCategoria.get();
            existingCategoria.setDenominacion(categoria.getDenominacion());
            existingCategoria.setCategoriaPadre(categoria.getCategoriaPadre());
            existingCategoria.setEmpresa(categoria.getEmpresa());

            return categoriaRepository.save(existingCategoria);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Categoria> findByEmpresaId(Long empresaId) {
        return categoriaRepository.findAll().stream()
                .filter(categoria -> categoria.getEmpresa() != null &&
                        categoria.getEmpresa().getId().equals(empresaId))
                .collect(Collectors.toList());
        // Nota: En un entorno real, deberías agregar un método en el repositorio
        // para hacer esta búsqueda directamente en la base de datos
    }
}
