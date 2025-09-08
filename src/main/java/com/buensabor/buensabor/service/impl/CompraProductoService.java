package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.dto.compraProducto.CompraPedidoDto;
import com.buensabor.buensabor.dto.compraProducto.CompraProductoDto;
import com.buensabor.buensabor.dto.compraProducto.PedidoDetalleDto;
import com.buensabor.buensabor.entities.*;
import com.buensabor.buensabor.enums.Estado;
import com.buensabor.buensabor.enums.Rol;
import com.buensabor.buensabor.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class CompraProductoService {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired private IArticuloManufacturadoRepository articuloManufacturadoRepository;
    @Autowired private IArticuloInsumoRepository articuloInsumoRepository;
    @Autowired private IArticuloRepository articuloRepository;
    @Autowired private IPedidoRepository pedidoRepository;
    @Autowired private ICategoriaRepository categoriaRepository;
    @Autowired private IClienteRepository clienteRepository;
    @Autowired private IEmpleadoRepository empleadoRepository;
    @Autowired private IDomicilioRepository domicilioRepository;

    /**
     * Devuelve SOLO MANUFACTURADOS (no insumos), tanto de la categoría padre como de sus subcategorías,
     * filtrando por “fabricable” según stock de insumos.
     */
    public List<CompraProductoDto> findArticulosByCategoria(Long categoriaId) {

        List<CompraProductoDto> articulos = new ArrayList<>();

        // Manufacturados de la categoría padre (solo los que se pueden fabricar)
        List<ArticuloManufacturado> amPadre =
                articuloManufacturadoRepository.findByCategoriaIdAndEliminadoFalse(categoriaId);
        for (ArticuloManufacturado am : amPadre) {
            if (puedeFabricarse(am, 1)) {
                articulos.add(convertToDto(am));
            }
        }

        // Manufacturados de subcategorías (solo los que se pueden fabricar)
        Set<Categoria> subcategorias =
                categoriaRepository.findByCategoriaPadre_IdAndEliminadoFalse(categoriaId);
        for (Categoria sub : subcategorias) {
            List<ArticuloManufacturado> amSub =
                    articuloManufacturadoRepository.findByCategoriaIdAndEliminadoFalse(sub.getId());
            for (ArticuloManufacturado am : amSub) {
                if (puedeFabricarse(am, 1)) {
                    articulos.add(convertToDto(am));
                }
            }
        }

        return articulos;
    }

    private boolean puedeFabricarse(ArticuloManufacturado am, int unidades) {
        if (am == null || am.getArticuloManufacturadoDetalles() == null) return false;
        for (ArticuloManufacturadoDetalle d : am.getArticuloManufacturadoDetalles()) {
            if (d == null || d.getArticuloInsumo() == null) return false;
            long necesario = (long) Math.max(1, d.getCantidad()) * Math.max(1, unidades);
            long stock = Optional.ofNullable(d.getArticuloInsumo().getStockActual())
                    .map(Integer::longValue).orElse(0L);
            if (stock < necesario) return false;
        }
        return true;
    }

    private CompraProductoDto convertToDto(Articulo articulo) {
        ArticuloManufacturado am = articuloManufacturadoRepository.findById(articulo.getId()).orElse(null);

        CompraProductoDto dto = new CompraProductoDto();
        dto.setId(articulo.getId());
        dto.setDenominacion(articulo.getDenominacion());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setCodigo(articulo.getCodigo());
        dto.setPrecioVenta(articulo.getPrecioVenta());

        if (am != null) {
            // MANUFACTURADO: tope por mínimos de (stockInsumo / cantidad requerida)
            dto.setPreparacion(am.getPreparacion());
            dto.setTiempoEstimadoMinutos(am.getTiempoEstimadoMinutos());
            long max = am.getArticuloManufacturadoDetalles().stream()
                    .mapToLong(d -> {
                        long divisor = Math.max(1L, d.getCantidad());
                        long stock = Optional.ofNullable(d.getArticuloInsumo())
                                .map(ArticuloInsumo::getStockActual)
                                .map(Integer::longValue)
                                .orElse(0L);
                        return stock / divisor; // división entera
                    })
                    .min().orElse(0L);
            dto.setCantidadMaximaCompra(max);
        } else {
            // No devolvemos insumos en el listado, pero por si llega otro tipo:
            dto.setCantidadMaximaCompra(0L);
        }

        // IMÁGENES: entity usa Set<ImagenArticulo>. Convertimos a List y limpiamos path.
        Set<ImagenArticulo> imagenesSet = Optional.ofNullable(articulo.getImagenes())
                .orElse(Collections.emptySet());
        List<ImagenArticulo> processedImages = new ArrayList<>(imagenesSet.size());
        for (ImagenArticulo imagen : imagenesSet) {
            ImagenArticulo copia = new ImagenArticulo();
            copia.setId(imagen.getId());
            String path = Optional.ofNullable(imagen.getUrl()).orElse("");
            path = path.replace("src\\main\\resources\\images\\", "");
            copia.setUrl(path);
            processedImages.add(copia);
        }
        dto.setImagenes(processedImages);

        if (articulo.getCategoria() != null) {
            dto.setCategoriaId(articulo.getCategoria().getId());
        }
        if (articulo.getSucursal() != null) {
            dto.setSucursalId(articulo.getSucursal().getId());
        }
        return dto;
    }

    public CompraProductoDto buscarArticuloPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Articulo no encontrado con id: " + id));

        CompraProductoDto dto = new CompraProductoDto();
        dto.setId(articulo.getId());
        dto.setDenominacion(articulo.getDenominacion());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setCodigo(articulo.getCodigo());
        dto.setPrecioVenta(articulo.getPrecioVenta());

        // Si es manufacturado, calculamos el tope igual que en el listado
        articuloManufacturadoRepository.findById(id).ifPresent(am -> {
            long max = am.getArticuloManufacturadoDetalles().stream()
                    .mapToLong(d -> {
                        long divisor = Math.max(1L, d.getCantidad());
                        long stock = Optional.ofNullable(d.getArticuloInsumo())
                                .map(ArticuloInsumo::getStockActual)
                                .map(Integer::longValue)
                                .orElse(0L);
                        return stock / divisor;
                    })
                    .min().orElse(0L);
            dto.setCantidadMaximaCompra(max);
            dto.setPreparacion(am.getPreparacion());
            dto.setTiempoEstimadoMinutos(am.getTiempoEstimadoMinutos());
        });

        // imágenes (Set -> List)
        dto.setImagenes(new ArrayList<>(Optional.ofNullable(articulo.getImagenes())
                .orElse(Collections.emptySet())));

        if (articulo.getCategoria() != null) {
            dto.setCategoriaId(articulo.getCategoria().getId());
        }
        if (articulo.getSucursal() != null) {
            dto.setSucursalId(articulo.getSucursal().getId());
        }
        return dto;
    }

    public CompraPedidoDto crearPedido(CompraPedidoDto compraPedidoDto) throws Exception {
        try {
            Map<Long, Integer> insumosNecesarios = new HashMap<>();
            int tiempoTotalEspera = 0;

            Long cantidadCocineros = empleadoRepository
                    .countBySucursalIdAndRolAndEliminadoFalse(compraPedidoDto.getSucursalId(), Rol.EMPLEADO_COCINA);

            int tiempoPreparacionEnCola =
                    calcularTiempoEsperaArticulosManufacturados(compraPedidoDto.getSucursalId());

            // Calcular insumos necesarios y tiempo base para manufacturados
            for (PedidoDetalleDto detalleDto : compraPedidoDto.getPedidoDetalle()) {
                Articulo articulo = articuloRepository.findById(detalleDto.getProducto().getId())
                        .orElseThrow(() -> new NoSuchElementException(
                                "Articulo no encontrado con id: " + detalleDto.getProducto().getId()));

                if (articulo instanceof ArticuloManufacturado) {
                    ArticuloManufacturado am = (ArticuloManufacturado) articulo;
                    tiempoTotalEspera += am.getTiempoEstimadoMinutos() * detalleDto.getCantidad();
                    for (ArticuloManufacturadoDetalle d : am.getArticuloManufacturadoDetalles()) {
                        insumosNecesarios.merge(
                                d.getArticuloInsumo().getId(),
                                d.getCantidad() * detalleDto.getCantidad(),
                                Integer::sum
                        );
                    }
                } else if (articulo instanceof ArticuloInsumo) {
                    // Si llegara un insumo por error desde el front, lo bloqueamos.
                    throw new Exception("No se pueden vender insumos en este flujo.");
                }
            }

            // Ajuste por carga de cocina
            tiempoTotalEspera += tiempoPreparacionEnCola;
            if (cantidadCocineros != null && cantidadCocineros.intValue() > 0) {
                tiempoTotalEspera = tiempoTotalEspera / cantidadCocineros.intValue();
            }

            // Verificación de stock de insumos
            for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
                ArticuloInsumo insumo = articuloInsumoRepository.findById(entry.getKey())
                        .orElseThrow(() -> new NoSuchElementException("Insumo no encontrado con id: " + entry.getKey()));
                if (insumo.getStockActual() < entry.getValue()) {
                    throw new Exception("Stock insuficiente para el insumo: " + insumo.getDenominacion());
                }
            }

            // Crear Pedido
            Pedido pedido = new Pedido();
            pedido.setFechaPedido(LocalDate.now());
            pedido.setHora(LocalTime.now());
            pedido.setTotal(compraPedidoDto.getTotal());

            // Recuperar domicilio de la base de datos para evitar detached entity
            Domicilio domicilio = domicilioRepository.findById(compraPedidoDto.getDomicilio().getId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Domicilio no encontrado con id: " + compraPedidoDto.getDomicilio().getId()));
            pedido.setDomicilio(domicilio);

            pedido.setTipoEnvio(compraPedidoDto.getTipoEnvio());
            pedido.setFormaPago(compraPedidoDto.getFormaPago());
            pedido.setCliente(clienteRepository.findById(compraPedidoDto.getCliente().getId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Cliente no encontrado con id: " + compraPedidoDto.getCliente().getId())));

            List<PedidoDetalle> pedidoDetalles = new ArrayList<>();
            for (PedidoDetalleDto detalleDto : compraPedidoDto.getPedidoDetalle()) {
                Articulo articulo = articuloRepository.findById(detalleDto.getProducto().getId())
                        .orElseThrow(() -> new NoSuchElementException(
                                "Articulo no encontrado con id: " + detalleDto.getProducto().getId()));

                if (articulo instanceof ArticuloManufacturado) {
                    ArticuloManufacturado am = (ArticuloManufacturado) articulo;
                    for (ArticuloManufacturadoDetalle d : am.getArticuloManufacturadoDetalles()) {
                        ArticuloInsumo ai = d.getArticuloInsumo();
                        ai.setStockActual(ai.getStockActual() - (d.getCantidad() * detalleDto.getCantidad()));
                        articuloRepository.save(ai);
                    }
                } else if (articulo instanceof ArticuloInsumo) {
                    // Doble seguridad
                    throw new Exception("No se pueden vender insumos en este flujo.");
                }

                PedidoDetalle det = new PedidoDetalle();
                det.setArticulo(articulo);
                det.setCantidad(detalleDto.getCantidad());
                det.setPedido(pedido);
                pedidoDetalles.add(det);
            }

            pedido.setPedidoDetalle(pedidoDetalles);
            if (!pedidoDetalles.isEmpty() && pedidoDetalles.get(0).getArticulo() != null) {
                pedido.setSucursal(pedidoDetalles.get(0).getArticulo().getSucursal());
            }
            pedido.setEstado(Estado.PENDIENTE);

            pedidoRepository.save(pedido);

            CompraPedidoDto pedidoDto = modelMapper.map(pedido, CompraPedidoDto.class);
            pedidoDto.setTiempoEspera(tiempoTotalEspera);
            return pedidoDto;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int calcularTiempoEsperaArticulosManufacturados(Long sucursalId) {
        List<Pedido> pedidosEnPreparacion =
                pedidoRepository.findBySucursalIdAndEstado(sucursalId, Estado.EN_PREPARACION);
        int tiempoTotalEspera = 0;

        for (Pedido pedido : pedidosEnPreparacion) {
            for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
                if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                    ArticuloManufacturado am = (ArticuloManufacturado) detalle.getArticulo();
                    tiempoTotalEspera += am.getTiempoEstimadoMinutos() * detalle.getCantidad();
                }
            }
        }
        return tiempoTotalEspera;
    }
}
