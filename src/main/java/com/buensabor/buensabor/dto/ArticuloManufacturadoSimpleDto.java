package com.buensabor.buensabor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloManufacturadoSimpleDto {
    private Long id;
    private String denominacion;
    private String descripcion;
    private String codigo;
    private Double precioVenta;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    private String categoriaNombre;
    private String unidadMedidaNombre;
    private List<InsumoDetalleDto> insumos;
}