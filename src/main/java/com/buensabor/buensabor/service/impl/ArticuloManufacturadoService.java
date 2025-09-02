package com.buensabor.buensabor.service.impl;


import com.buensabor.buensabor.dto.articuloInsumo.ArticuloInsumoDto;
import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoDetalleDto;
import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoDto;
import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoTablaDto;
import com.buensabor.buensabor.dto.categoria.CategoriaDto;
import com.buensabor.buensabor.entities.*;
import com.buensabor.buensabor.mapeosDto.MapeoDto;
import com.buensabor.buensabor.repository.IArticuloManufacturadoDetalleRepository;
import com.buensabor.buensabor.repository.IArticuloManufacturadoRepository;
import com.buensabor.buensabor.repository.IImagenArticuloRepository;
import com.buensabor.buensabor.service.IArticuloManufacturadoService;
import com.buensabor.buensabor.service.funcionalidades.Funcionalidades;
import com.buensabor.buensabor.service.util.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ArticuloManufacturadoService implements IArticuloManufacturadoService {

    @Autowired
    private IArticuloManufacturadoRepository articuloManufacturadoRepository;
    @Autowired
    private IArticuloManufacturadoDetalleRepository detalleRepository;
    @Autowired
    private IImagenArticuloRepository imagenRepository;
    @Autowired
    private ImagenService imagenService;
    @Autowired
    private Funcionalidades funcionalidades;
    @Autowired
    private MapeoDto mapeoDto;

//region Crud Basico

    //region Cargar

    @Override
    public ArticuloManufacturado cargarArticuloManufacturado(ArticuloManufacturado articuloManufacturado) throws Exception {
        try {
            Long sucursalId = articuloManufacturado.getSucursal().getId();

            if (articuloManufacturadoRepository.existsByDenominacionAndSucursal_IdAndEliminadoFalse(articuloManufacturado.getDenominacion(), sucursalId)) {
                throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
            }
            if (articuloManufacturadoRepository.existsByCodigoAndSucursal_IdAndEliminadoFalse(articuloManufacturado.getCodigo(), sucursalId)) {
                throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
            }

            if (articuloManufacturado.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloManufacturado.getImagenes()) {
                    // Generar un nombre de archivo único para cada imagen
                    String filename = UUID.randomUUID().toString() + ".jpg";

                    // Utilizar la función guardarImagen de Funcionalidades para guardar la imagen
                    String ruta = funcionalidades.guardarImagen(imagen.getUrl(), filename);

                    // Actualizar el campo url en ImagenArticulo
                    imagen.setUrl(ruta);
                    imagen.setArticulo(articuloManufacturado);
                }
            }

            for (ArticuloManufacturadoDetalle detalle : articuloManufacturado.getArticuloManufacturadoDetalles()) {
                detalle.setArticuloManufacturado(articuloManufacturado);
            }

            return articuloManufacturadoRepository.save(articuloManufacturado);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //endregion

    //region Buscar por ID
    @Override
    public ArticuloManufacturado buscarPorId(Long id) throws Exception {
        try {
            ArticuloManufacturado Manufacturado = articuloManufacturadoRepository.findByIdAndEliminadoFalse(id);
            if (Manufacturado == null) {
                throw new Exception("No se encontro el articulo");
            }

            // Convertir las imágenes a base64
            if (Manufacturado.getImagenes() != null) {
                for (ImagenArticulo imagen : Manufacturado.getImagenes()) {
                    try {
                        String imagenBase64 = imagenService.convertirImagenABase64Nueva(imagen.getUrl());
                        imagen.setUrl(imagenBase64); // Actualizar el campo url en ImagenArticulo con la imagen en base64
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            return Manufacturado;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
//endregion

    //region Mostrar Lista
    @Override
    public Set<ArticuloManufacturado> listaArticuloManufacturado() throws Exception {
        try {
            Set<ArticuloManufacturado> articulosManufacturados = articuloManufacturadoRepository.findByEliminadoFalse();


            for (ArticuloManufacturado articuloManufacturado : articulosManufacturados) {
                if (articuloManufacturado.getImagenes() != null) {
                    for (ImagenArticulo imagen : articuloManufacturado.getImagenes()) {
                        try {
                            String imagenBase64 = imagenService.convertirImagenABase64Nueva(imagen.getUrl());
                            imagen.setUrl(imagenBase64);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            return articulosManufacturados;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //endregion

    //region Eliminacion
    @Override
    public boolean eliminarArticuloManufacturado(Long id) throws Exception {
        try {
            ArticuloManufacturado articuloManufacturado = articuloManufacturadoRepository.findById(id).orElse(null);
            if (articuloManufacturado == null) {
                throw new Exception("No se encontro el articulo");
            }
            articuloManufacturado.setEliminado(true);


            for (ArticuloManufacturadoDetalle detalle : articuloManufacturado.getArticuloManufacturadoDetalles()) {
                detalle.setEliminado(true);
                detalleRepository.save(detalle);
            }


            for (ImagenArticulo imagen : articuloManufacturado.getImagenes()) {
                imagen.setEliminado(true);
                imagenRepository.save(imagen);
            }

            articuloManufacturadoRepository.save(articuloManufacturado);
            return true;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    //endregion

    //region Actualizar

    @Override
    public ArticuloManufacturadoDto actualizarArticuloManufacturado(Long id, ArticuloManufacturado articuloManufacturado) throws Exception {
        try {
            if (!articuloManufacturadoRepository.existsById(id)){
                throw new Exception("No se encontro el articulo");
            }

            ArticuloManufacturado articuloManufacturadoViejo = articuloManufacturadoRepository.findById(id).get();
            Long sucursalId = articuloManufacturadoViejo.getSucursal().getId();

            if(articuloManufacturadoRepository.existsByCodigoAndSucursal_Id(articuloManufacturado.getCodigo(), sucursalId) && !articuloManufacturado.getCodigo().equals(articuloManufacturadoViejo.getCodigo())){
                throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
            }
            if (articuloManufacturadoRepository.existsByDenominacionAndSucursal_Id(articuloManufacturado.getDenominacion(), sucursalId) && !articuloManufacturado.getDenominacion().equals(articuloManufacturadoViejo.getDenominacion())){
                throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
            }


            Set<ArticuloManufacturadoDetalle> detallesViejos = detalleRepository.findByArticuloManufacturado_Id(id);
            Set<ArticuloManufacturadoDetalle> detallesNuevos = articuloManufacturado.getArticuloManufacturadoDetalles();

            detallesViejos.forEach(detalleViejo -> {
                if (!detallesNuevos.contains(detalleViejo)) {
                    detalleViejo.setEliminado(true);
                    detalleViejo.setArticuloManufacturado(null);
                    detalleRepository.save(detalleViejo);
                }
            });
            //endregion


            Set<ImagenArticulo> imagenesViejas = imagenRepository.findByArticulo_Id(id);
            Set<ImagenArticulo> imagenesNuevas = articuloManufacturado.getImagenes();

            imagenesViejas.forEach(imagenVieja -> {
                if (!imagenesNuevas.contains(imagenVieja)) {
                    imagenVieja.setEliminado(true);
                    imagenVieja.setArticulo(null);
                    imagenRepository.save(imagenVieja);
                    funcionalidades.eliminarImagen(imagenVieja.getUrl());
                    imagenRepository.delete(imagenVieja);
                }
            });

            if (articuloManufacturado.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloManufacturado.getImagenes()) {

                    String filename = UUID.randomUUID().toString() + ".jpg";
                    try {
                        String rutaImagen = funcionalidades.guardarImagen(imagen.getUrl(), filename);
                        imagen.setUrl(rutaImagen);
                        imagen.setArticulo(articuloManufacturado);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    boolean exists = imagenesViejas.stream().anyMatch(oldImage -> oldImage.getUrl().equals(imagen.getUrl()));


                    if (!exists && imagenesNuevas.contains(imagen)) {
                        imagenRepository.save(imagen);
                    }
                }
            }
            // ELIMINAR ESTAS DOS LÍNEAS:
            // articuloManufacturado.setPrecioVenta(articuloManufacturadoViejo.getPrecioVenta());
            // articuloManufacturado.setTiempoEstimadoMinutos(articuloManufacturadoViejo.getTiempoEstimadoMinutos());


            articuloManufacturado.setSucursal(articuloManufacturadoViejo.getSucursal());
            ArticuloManufacturadoDto dto= mapeoDto.convertManufacturadoDto(articuloManufacturadoRepository.save(articuloManufacturado));
            return dto;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    //endregion

    //endregion

    //region Dtos

    public Set<ArticuloManufacturadoTablaDto> tablaArticuloManufacturado() throws Exception {
        try {
            Set<ArticuloManufacturado> articulos = articuloManufacturadoRepository.findByEliminadoFalse();
            Set<ArticuloManufacturadoTablaDto> articulosDto = new HashSet<>();

            for (ArticuloManufacturado articulo : articulos) {
                ArticuloManufacturadoTablaDto dto = new ArticuloManufacturadoTablaDto();
                dto.setId(articulo.getId());
                dto.setCodigo(articulo.getCodigo());
                dto.setDenominacion(articulo.getDenominacion());

                // Convertir la imagen a base64
                if (articulo.getImagenes() != null && !articulo.getImagenes().isEmpty()) {
                    ImagenArticulo imagen = articulo.getImagenes().iterator().next();
                    try {
                        String imagenBase64 = imagenService.convertirImagenABase64Nueva(imagen.getUrl());
                        dto.setImagen(imagenBase64);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                dto.setPrecioVenta(articulo.getPrecioVenta());
                dto.setDescripcion(articulo.getDescripcion());
                dto.setTiempoEstimadoMinutos(articulo.getTiempoEstimadoMinutos());

                articulosDto.add(dto);
            }

            return articulosDto;

        }catch (Exception e){
            throw new Exception(e);
        }
    }


    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            ArticuloManufacturado articuloManufacturado = articuloManufacturadoRepository.findById(id).orElse(null);
            if (articuloManufacturado == null) {
                throw new Exception("No se encontro el articulo");
            }
            articuloManufacturado.setEliminado(false);

            for (ArticuloManufacturadoDetalle detalle : articuloManufacturado.getArticuloManufacturadoDetalles()) {
                detalle.setEliminado(false);
                detalleRepository.save(detalle);
            }

            for (ImagenArticulo imagen : articuloManufacturado.getImagenes()) {
                imagen.setEliminado(false);
                imagenRepository.save(imagen);
            }

            articuloManufacturadoRepository.save(articuloManufacturado);
            return true;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    public List<ArticuloManufacturado> traerTodos() throws Exception {
        try {
            return articuloManufacturadoRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public ArticuloManufacturadoDto traerArticuloBase64(Long id) throws Exception {
        try {
            ArticuloManufacturado Manufacturado = articuloManufacturadoRepository.findByIdAndEliminadoFalse(id);
            if (Manufacturado == null) {
                throw new Exception("No se encontro el articulo");
            }


            if (Manufacturado.getImagenes() != null) {
                for (ImagenArticulo imagen : Manufacturado.getImagenes()) {
                    try {
                        String imagenBase64 = funcionalidades.convertirImagenABase64(imagen.getUrl());
                        imagen.setUrl(imagenBase64);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            return mapeoDto.convertManufacturadoDto(Manufacturado);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }




    //endregion
}


