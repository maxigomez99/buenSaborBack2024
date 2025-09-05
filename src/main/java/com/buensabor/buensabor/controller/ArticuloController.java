package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.ArticulosParaVentaDto;
import com.buensabor.buensabor.dto.ArticuloManufacturadoSimpleDto;
import com.buensabor.buensabor.dto.ArticuloInsumoSimpleDto;
import com.buensabor.buensabor.entities.ArticuloInsumo;
import com.buensabor.buensabor.entities.ArticuloManufacturado;
import com.buensabor.buensabor.repository.IArticuloInsumoRepository;
import com.buensabor.buensabor.repository.IArticuloManufacturadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    private static final Logger logger = LoggerFactory.getLogger(ArticuloController.class);

    @Autowired
    private IArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private IArticuloManufacturadoRepository articuloManufacturadoRepository;

    @GetMapping("/para-venta/{sucursalId}")
    public ResponseEntity<?> obtenerArticulosParaVenta(@PathVariable Long sucursalId) {
        logger.info("Iniciando búsqueda de artículos para venta en sucursal: {}", sucursalId);

        try {
            if (sucursalId == null || sucursalId <= 0) {
                logger.warn("ID de sucursal inválido: {}", sucursalId);
                return ResponseEntity.badRequest().body("ID de sucursal inválido");
            }

            // Obtener artículos manufacturados de la sucursal
            List<ArticuloManufacturado> manufacturados = articuloManufacturadoRepository.findBySucursal_Id(sucursalId);
            logger.info("Encontrados {} artículos manufacturados en sucursal {}", manufacturados.size(), sucursalId);

            // Convertir a DTO y filtrar no eliminados
            List<ArticuloManufacturadoSimpleDto> manufacturadosDto = manufacturados.stream()
                    .filter(articulo -> articulo != null && !articulo.isEliminado())
                    .map(this::convertirAManufacturadoDto)
                    .toList();
            logger.info("Después del filtro: {} artículos manufacturados no eliminados", manufacturadosDto.size());

            // Obtener insumos de la sucursal
            Set<ArticuloInsumo> insumosSet = articuloInsumoRepository.findBySucursal_Id(sucursalId);
            logger.info("Encontrados {} insumos en sucursal {}", insumosSet.size(), sucursalId);

            // Convertir a DTO y filtrar
            List<ArticuloInsumoSimpleDto> insumosDto = insumosSet.stream()
                    .filter(insumo -> insumo != null &&
                            !insumo.isEliminado() &&
                            (insumo.getEsParaElaborar() == null || !insumo.getEsParaElaborar()))
                    .map(this::convertirAInsumoDto)
                    .toList();
            logger.info("Después del filtro: {} insumos no eliminados y no para elaborar", insumosDto.size());

            // Crear respuesta con DTOs simples
            ArticulosParaVentaDto response = new ArticulosParaVentaDto();
            response.setArticulosManufacturados(manufacturadosDto);
            response.setArticulosInsumos(insumosDto);

            logger.info("Respuesta creada exitosamente para sucursal {}", sucursalId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al obtener artículos para venta en sucursal {}: {}", sucursalId, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    private ArticuloManufacturadoSimpleDto convertirAManufacturadoDto(ArticuloManufacturado articulo) {
        ArticuloManufacturadoSimpleDto dto = new ArticuloManufacturadoSimpleDto();
        dto.setId(articulo.getId());
        dto.setDenominacion(articulo.getDenominacion());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setCodigo(articulo.getCodigo());
        dto.setPrecioVenta(articulo.getPrecioVenta());
        dto.setTiempoEstimadoMinutos(articulo.getTiempoEstimadoMinutos());
        dto.setPreparacion(articulo.getPreparacion());
        dto.setCategoriaNombre(articulo.getCategoria() != null ? articulo.getCategoria().getDenominacion() : null);
        dto.setUnidadMedidaNombre(articulo.getUnidadMedida() != null ? articulo.getUnidadMedida().getDenominacion() : null);
        return dto;
    }

    private ArticuloInsumoSimpleDto convertirAInsumoDto(ArticuloInsumo insumo) {
        ArticuloInsumoSimpleDto dto = new ArticuloInsumoSimpleDto();
        dto.setId(insumo.getId());
        dto.setDenominacion(insumo.getDenominacion());
        dto.setDescripcion(insumo.getDescripcion());
        dto.setCodigo(insumo.getCodigo());
        dto.setPrecioVenta(insumo.getPrecioVenta());
        dto.setPrecioCompra(insumo.getPrecioCompra());
        dto.setStockActual(insumo.getStockActual());
        dto.setStockMaximo(insumo.getStockMaximo());
        dto.setStockMinimo(insumo.getStockMinimo());
        dto.setEsParaElaborar(insumo.getEsParaElaborar());
        dto.setCategoriaNombre(insumo.getCategoria() != null ? insumo.getCategoria().getDenominacion() : null);
        dto.setUnidadMedidaNombre(insumo.getUnidadMedida() != null ? insumo.getUnidadMedida().getDenominacion() : null);
        return dto;
    }
}
