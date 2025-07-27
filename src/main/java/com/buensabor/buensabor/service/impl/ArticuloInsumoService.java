package com.buensabor.buensabor.service.impl;



import com.buensabor.buensabor.entities.ArticuloInsumo;
import com.buensabor.buensabor.entities.ImagenArticulo;
import com.buensabor.buensabor.repository.IArticuloInsumoRepository;
import com.buensabor.buensabor.repository.IArticuloManufacturadoDetalleRepository;
import com.buensabor.buensabor.repository.IImagenArticuloRepository;
import com.buensabor.buensabor.service.IArticuloInsumoService;
import com.buensabor.buensabor.service.funcionalidades.Funcionalidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ArticuloInsumoService implements IArticuloInsumoService {
    private final Path root = Paths.get("images");

    @Autowired
    private IArticuloInsumoRepository articuloInsumoRepository;
    @Autowired
    private IImagenArticuloRepository imagenRepository;
    @Autowired
    private IArticuloManufacturadoDetalleRepository articuloManufacturadoRepository;
    @Autowired
    Funcionalidades funcionalidades;

    //region Eliminacion
    @Override
    public boolean deleteById(Long id) throws Exception {

        try {
            ArticuloInsumo articuloInsumo = articuloInsumoRepository.findById(id).orElseThrow(() -> new Exception("No se encontro el articulo"));


            if (articuloManufacturadoRepository.existsByArticuloInsumo_IdAndEliminadoFalse(id)) {
                throw new Exception("No se puede eliminar el articulo porque está asignado a un ArticuloManufacturado");
            }


            Set<ImagenArticulo> imagenes = articuloInsumo.getImagenes();
            if (imagenes != null) {
                for (ImagenArticulo imagen : imagenes) {
                    imagen.setEliminado(true);
                    imagenRepository.save(imagen);
                }
            }

            articuloInsumo.setEliminado(true);
            articuloInsumoRepository.save(articuloInsumo);
            return true;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    //endregion

    //region Mostrar Lista
    @Override
    public List<ArticuloInsumo> mostrarLista() throws Exception {
        try {
            List<ArticuloInsumo> articulos = articuloInsumoRepository.findByEliminadoFalse();
            for (ArticuloInsumo articulo : articulos) {
                if (articulo.getImagenes() != null) {
                    for (ImagenArticulo imagen : articulo.getImagenes()) {
                        String imagenBase64 = funcionalidades.convertirImagenABase64(imagen.getUrl());
                        imagen.setUrl(imagenBase64); // Actualizar el campo url en ImagenArticulo con la imagen en base64
                    }
                }
            }
            return articulos;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public ArticuloInsumo cargar(ArticuloInsumo articuloInsumo) throws Exception {
        try {
            Long sucursalId = articuloInsumo.getSucursal().getId();

            if (articuloInsumoRepository.existsByCodigoAndSucursal_Id(articuloInsumo.getCodigo(), sucursalId)) {
                throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
            }
            if (articuloInsumoRepository.existsByDenominacionAndSucursal_Id(articuloInsumo.getDenominacion(), sucursalId)) {
                throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
            }


            if (articuloInsumo.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    String filename = UUID.randomUUID().toString() + ".jpg";
                    String ruta = funcionalidades.guardarImagen(imagen.getUrl(), filename);
                    imagen.setUrl(ruta);
                    imagen.setArticulo(articuloInsumo);
                }
            }

            articuloInsumoRepository.save(articuloInsumo);
            return articuloInsumoRepository.findById(articuloInsumo.getId()).get();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //endregion

    //region Buscar Por Id
    @Override
    public ArticuloInsumo buscarPorId(Long id) throws Exception {
        try {
            ArticuloInsumo articuloInsumo = articuloInsumoRepository.findByIdAndEliminadoFalse(id);
            if (articuloInsumo == null) {
                throw new Exception("No se encontro el articulo");
            }

            if (articuloInsumo.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    String imagenBase64 = funcionalidades.convertirImagenABase64(imagen.getUrl());
                    imagen.setUrl(imagenBase64); // Actualizar el campo url en ImagenArticulo con la imagen en base64
                }
            }

            return articuloInsumo;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public ArticuloInsumo buscarPorIdBase64(Long id) throws Exception {
        try {
            ArticuloInsumo articuloInsumo = articuloInsumoRepository.findByIdAndEliminadoFalse(id);
            if (articuloInsumo == null) {
                throw new Exception("No se encontro el articulo");
            }

            // NO convertir las imágenes - mantenerlas como base64
            // (Sin procesamiento de archivos)

            return articuloInsumo;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    //endregion

    //region Actualizar
    @Override
    public ArticuloInsumo actualizar(Long id, ArticuloInsumo articuloInsumo) throws Exception {
        try {
            if (!articuloInsumoRepository.existsById(id)) {
                throw new Exception("No se encontro el articulo");
            }

            ArticuloInsumo articuloInsumoViejo = articuloInsumoRepository.findById(id).get();
            Long sucursalId = articuloInsumoViejo.getSucursal().getId();

            if (articuloInsumoRepository.existsByCodigoAndSucursal_Id(articuloInsumo.getCodigo(), sucursalId) && !articuloInsumo.getCodigo().equals(articuloInsumoViejo.getCodigo())) {
                throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
            }
            if (articuloInsumoRepository.existsByDenominacionAndSucursal_Id(articuloInsumo.getDenominacion(), sucursalId) && !articuloInsumo.getDenominacion().equals(articuloInsumoViejo.getDenominacion())) {
                throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
            }


            //region Logica para eliminar Imagenes
            Set<ImagenArticulo> imagenesViejas = imagenRepository.findByArticulo_Id(id);
            Set<ImagenArticulo> imagenesNuevas = articuloInsumo.getImagenes();

            imagenesViejas.forEach(imagenVieja -> {
                if (!imagenesNuevas.contains(imagenVieja)) {
                    imagenVieja.setEliminado(true);
                    imagenVieja.setArticulo(null);
                    imagenRepository.save(imagenVieja);
                    funcionalidades.eliminarImagen(imagenVieja.getUrl());
                    imagenRepository.delete(imagenVieja);
                }
            });

            if (articuloInsumo.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    String filename = UUID.randomUUID().toString() + ".jpg";
                    try {
                        String rutaImagen = funcionalidades.guardarImagen(imagen.getUrl(), filename);
                        imagen.setUrl(rutaImagen);
                        imagen.setArticulo(articuloInsumo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    boolean exists = imagenesViejas.stream().anyMatch(oldImage -> oldImage.getUrl().equals(imagen.getUrl()));

                    if (!exists && imagenesNuevas.contains(imagen)) {
                        imagenRepository.save(imagen);
                    }
                }
            }
            articuloInsumo.setPrecioCompra(articuloInsumoViejo.getPrecioCompra());
            articuloInsumo.setPrecioVenta(articuloInsumoViejo.getPrecioVenta());
            articuloInsumo.setStockActual(articuloInsumoViejo.getStockActual());
            articuloInsumo.setSucursal(articuloInsumoViejo.getSucursal());

            return articuloInsumoRepository.save(articuloInsumo);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    //endregion


    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            if (articuloInsumoRepository.existsById(id)) {
                ArticuloInsumo articuloInsumo = articuloInsumoRepository.findById(id).get();
                if (articuloInsumo.isEliminado()) {
                    articuloInsumo.setEliminado(false);

                    // Reactivar las ImagenArticulo asociadas
                    for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                        if (imagen.isEliminado()) {
                            imagen.setEliminado(false);
                        }
                    }

                    articuloInsumoRepository.save(articuloInsumo);
                    return true;
                } else {
                    throw new Exception("El ArticuloInsumo con el id proporcionado no está eliminado");
                }
            } else {
                throw new Exception("No existe el ArticuloInsumo con el id proporcionado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ArticuloInsumo> traerTodo() throws Exception {
        try {
            return articuloInsumoRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public ArticuloInsumo toggleEstado(Long id) throws Exception {
        try {
            ArticuloInsumo articuloInsumo = articuloInsumoRepository.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró el artículo insumo con id: " + id));

            // Cambiar el estado de eliminado
            articuloInsumo.setEliminado(!articuloInsumo.isEliminado());

            // Si se está activando, también activar las imágenes asociadas
            if (!articuloInsumo.isEliminado() && articuloInsumo.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    imagen.setEliminado(false);
                }
            }
            // Si se está desactivando, también desactivar las imágenes asociadas
            else if (articuloInsumo.isEliminado() && articuloInsumo.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    imagen.setEliminado(true);
                }
            }

            return articuloInsumoRepository.save(articuloInsumo);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ArticuloInsumo cargarConImagenesBase64(ArticuloInsumo articuloInsumo) throws Exception {
        try {
            Long sucursalId = articuloInsumo.getSucursal().getId();

            if (articuloInsumoRepository.existsByCodigoAndSucursal_Id(articuloInsumo.getCodigo(), sucursalId)) {
                throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
            }
            if (articuloInsumoRepository.existsByDenominacionAndSucursal_Id(articuloInsumo.getDenominacion(), sucursalId)) {
                throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
            }

            // Guardar las imágenes directamente en base64 sin convertir a archivos
            if (articuloInsumo.getImagenes() != null) {
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    // Mantener la imagen en base64 tal como viene del frontend
                    imagen.setArticulo(articuloInsumo);
                }
            }

            articuloInsumoRepository.save(articuloInsumo);
            return articuloInsumoRepository.findById(articuloInsumo.getId()).get();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public ArticuloInsumo actualizarConImagenesBase64(Long id, ArticuloInsumo articuloInsumo) throws Exception {
        try {
            if (!articuloInsumoRepository.existsById(id)) {
                throw new Exception("No se encontro el articulo");
            }

            ArticuloInsumo articuloInsumoViejo = articuloInsumoRepository.findById(id).get();
            Long sucursalId = articuloInsumoViejo.getSucursal().getId();

            // Validar código único si cambió
            if (!articuloInsumoViejo.getCodigo().equals(articuloInsumo.getCodigo())) {
                if (articuloInsumoRepository.existsByCodigoAndSucursal_Id(articuloInsumo.getCodigo(), sucursalId)) {
                    throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
                }
            }

            // Validar denominación única si cambió
            if (!articuloInsumoViejo.getDenominacion().equals(articuloInsumo.getDenominacion())) {
                if (articuloInsumoRepository.existsByDenominacionAndSucursal_Id(articuloInsumo.getDenominacion(), sucursalId)) {
                    throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
                }
            }

            // Actualizar campos básicos
            articuloInsumoViejo.setDenominacion(articuloInsumo.getDenominacion());
            articuloInsumoViejo.setDescripcion(articuloInsumo.getDescripcion());
            articuloInsumoViejo.setCodigo(articuloInsumo.getCodigo());
            articuloInsumoViejo.setPrecioVenta(articuloInsumo.getPrecioVenta());
            articuloInsumoViejo.setPrecioCompra(articuloInsumo.getPrecioCompra());
            articuloInsumoViejo.setStockActual(articuloInsumo.getStockActual());
            articuloInsumoViejo.setStockMaximo(articuloInsumo.getStockMaximo());
            articuloInsumoViejo.setStockMinimo(articuloInsumo.getStockMinimo());
            articuloInsumoViejo.setEsParaElaborar(articuloInsumo.getEsParaElaborar());

            // Actualizar relaciones si se proporcionan
            if (articuloInsumo.getCategoria() != null) {
                articuloInsumoViejo.setCategoria(articuloInsumo.getCategoria());
            }
            if (articuloInsumo.getUnidadMedida() != null) {
                articuloInsumoViejo.setUnidadMedida(articuloInsumo.getUnidadMedida());
            }
            if (articuloInsumo.getSucursal() != null) {
                articuloInsumoViejo.setSucursal(articuloInsumo.getSucursal());
            }

            // Manejar imágenes: eliminar las anteriores y agregar las nuevas en base64
            if (articuloInsumo.getImagenes() != null) {
                // PASO 1: Eliminar imágenes anteriores
                if (articuloInsumoViejo.getImagenes() != null) {
                    for (ImagenArticulo imagenAnterior : articuloInsumoViejo.getImagenes()) {
                        imagenAnterior.setEliminado(true);
                        imagenRepository.save(imagenAnterior);
                    }
                    // Limpiar la colección
                    articuloInsumoViejo.getImagenes().clear();
                }

                // PASO 2: Guardar el artículo SIN imágenes primero
                articuloInsumoRepository.save(articuloInsumoViejo);

                // PASO 3: Crear nuevas imágenes una por una para evitar problemas
                Set<ImagenArticulo> nuevasImagenes = new HashSet<>();
                for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                    try {
                        // Crear imagen completamente nueva usando constructor
                        ImagenArticulo nuevaImagen = new ImagenArticulo();
                        nuevaImagen.setUrl(imagen.getUrl()); // Base64 directo - SIN validación de archivo
                        nuevaImagen.setArticulo(articuloInsumoViejo);
                        nuevaImagen.setEliminado(false);

                        // Guardar directamente en repository
                        ImagenArticulo imagenGuardada = imagenRepository.save(nuevaImagen);
                        nuevasImagenes.add(imagenGuardada);
                    } catch (Exception e) {
                        // Log del error específico para debug
                        System.err.println("Error guardando imagen base64: " + e.getMessage());
                        throw new Exception("Error procesando imagen: " + e.getMessage());
                    }
                }

                // PASO 4: Actualizar la relación
                articuloInsumoViejo.setImagenes(nuevasImagenes);
            }

            // PASO 5: Guardar final con las imágenes
            articuloInsumoRepository.save(articuloInsumoViejo);
            return articuloInsumoRepository.findById(id).get();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
