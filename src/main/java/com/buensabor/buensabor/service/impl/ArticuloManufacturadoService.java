package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoDto;
import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoTablaDto;
import com.buensabor.buensabor.entities.ArticuloManufacturado;
import com.buensabor.buensabor.repository.IArticuloManufacturadoRepository;
import com.buensabor.buensabor.service.IArticuloManufacturadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticuloManufacturadoService implements IArticuloManufacturadoService {

    @Autowired
    private IArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Override
    public ArticuloManufacturado cargarArticuloManufacturado(ArticuloManufacturado articuloManufacturado) throws Exception {
        try {
            return articuloManufacturadoRepository.save(articuloManufacturado);
        } catch (Exception e) {
            throw new Exception("Error al cargar el artículo manufacturado: " + e.getMessage());
        }
    }

    @Override
    public ArticuloManufacturado buscarPorId(Long id) throws Exception {
        try {
            Optional<ArticuloManufacturado> optionalArticuloManufacturado = articuloManufacturadoRepository.findById(id);
            if(optionalArticuloManufacturado.isPresent()) {
                return optionalArticuloManufacturado.get();
            } else {
                throw new Exception("No se encontró el artículo manufacturado con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar el artículo manufacturado: " + e.getMessage());
        }
    }

    @Override
    public Set<ArticuloManufacturado> listaArticuloManufacturado() throws Exception {
        try {
            List<ArticuloManufacturado> lista = articuloManufacturadoRepository.findAll();
            return new HashSet<>(lista);
        } catch (Exception e) {
            throw new Exception("Error al obtener la lista de artículos manufacturados: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminarArticuloManufacturado(Long id) throws Exception {
        try {
            if(articuloManufacturadoRepository.existsById(id)) {
                ArticuloManufacturado articulo = articuloManufacturadoRepository.findById(id).get();
                articulo.setEliminado(true);
                articuloManufacturadoRepository.save(articulo);
                return true;
            } else {
                throw new Exception("No se encontró el artículo manufacturado con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar el artículo manufacturado: " + e.getMessage());
        }
    }

    @Override
    public ArticuloManufacturadoDto actualizarArticuloManufacturado(Long id, ArticuloManufacturado articuloManufacturado) throws Exception {
        try {
            Optional<ArticuloManufacturado> optionalArticuloManufacturado = articuloManufacturadoRepository.findById(id);

            if(optionalArticuloManufacturado.isPresent()) {
                ArticuloManufacturado existingArticulo = optionalArticuloManufacturado.get();
                existingArticulo.setDenominacion(articuloManufacturado.getDenominacion());
                existingArticulo.setDescripcion(articuloManufacturado.getDescripcion());
                existingArticulo.setTiempoEstimadoMinutos(articuloManufacturado.getTiempoEstimadoMinutos());
                existingArticulo.setPreparacion(articuloManufacturado.getPreparacion());
                existingArticulo.setPrecioVenta(articuloManufacturado.getPrecioVenta());
                existingArticulo.setCategoria(articuloManufacturado.getCategoria());

                ArticuloManufacturado saved = articuloManufacturadoRepository.save(existingArticulo);

                // Convertir a DTO
                ArticuloManufacturadoDto dto = new ArticuloManufacturadoDto();
                dto.setId(saved.getId());
                dto.setDenominacion(saved.getDenominacion());
                dto.setDescripcion(saved.getDescripcion());
                dto.setTiempoEstimadoMinutos(saved.getTiempoEstimadoMinutos());
                dto.setPreparacion(saved.getPreparacion());
                dto.setPrecioVenta(saved.getPrecioVenta());
                if (saved.getCategoria() != null) {
                    dto.setCategoriaId(saved.getCategoria().getId());
                }

                return dto;
            } else {
                throw new Exception("No se encontró el artículo manufacturado con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar el artículo manufacturado: " + e.getMessage());
        }
    }

    @Override
    public Set<ArticuloManufacturadoTablaDto> tablaArticuloManufacturado() throws Exception {
        try {
            List<ArticuloManufacturado> articulos = articuloManufacturadoRepository.findByEliminadoFalse();
            Set<ArticuloManufacturadoTablaDto> dtos = new HashSet<>();

            for (ArticuloManufacturado articulo : articulos) {
                ArticuloManufacturadoTablaDto dto = new ArticuloManufacturadoTablaDto();
                dto.setId(articulo.getId());
                dto.setDenominacion(articulo.getDenominacion());
                dto.setDescripcion(articulo.getDescripcion());
                dto.setTiempoEstimadoMinutos(articulo.getTiempoEstimadoMinutos());
                dto.setPrecioVenta(articulo.getPrecioVenta());
                if (articulo.getCategoria() != null) {
                    dto.setCategoriaDenominacion(articulo.getCategoria().getDenominacion());
                }
                dtos.add(dto);
            }

            return dtos;
        } catch (Exception e) {
            throw new Exception("Error al obtener la tabla de artículos manufacturados: " + e.getMessage());
        }
    }

    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            Optional<ArticuloManufacturado> optionalArticulo = articuloManufacturadoRepository.findById(id);
            if (optionalArticulo.isPresent()) {
                ArticuloManufacturado articulo = optionalArticulo.get();
                if (articulo.isEliminado()) {
                    articulo.setEliminado(false);
                    articuloManufacturadoRepository.save(articulo);
                    return true;
                } else {
                    throw new Exception("El artículo manufacturado no está eliminado");
                }
            } else {
                throw new Exception("No se encontró el artículo manufacturado con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al reactivar el artículo manufacturado: " + e.getMessage());
        }
    }

    @Override
    public List<ArticuloManufacturado> traerTodos() throws Exception {
        try {
            return articuloManufacturadoRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener todos los artículos manufacturados: " + e.getMessage());
        }
    }

    @Override
    public ArticuloManufacturadoDto traerArticuloBase64(Long id) throws Exception {
        try {
            Optional<ArticuloManufacturado> optionalArticulo = articuloManufacturadoRepository.findById(id);

            if (optionalArticulo.isPresent()) {
                ArticuloManufacturado articulo = optionalArticulo.get();
                ArticuloManufacturadoDto dto = new ArticuloManufacturadoDto();
                dto.setId(articulo.getId());
                dto.setDenominacion(articulo.getDenominacion());
                dto.setDescripcion(articulo.getDescripcion());
                dto.setTiempoEstimadoMinutos(articulo.getTiempoEstimadoMinutos());
                dto.setPreparacion(articulo.getPreparacion());
                dto.setPrecioVenta(articulo.getPrecioVenta());

                if (articulo.getCategoria() != null) {
                    dto.setCategoriaId(articulo.getCategoria().getId());
                }

                // Aquí podrías agregar lógica para manejar imágenes en base64 si es necesario

                return dto;
            } else {
                throw new Exception("No se encontró el artículo manufacturado con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener el artículo manufacturado con imágenes en base64: " + e.getMessage());
        }
    }

    @Override
    public List<ArticuloManufacturado> findByCategoriaId(Long categoriaId) throws Exception {
        try {
            return articuloManufacturadoRepository.findByCategoriaIdAndEliminadoFalse(categoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos manufacturados por categoría: " + e.getMessage());
        }
    }
}
