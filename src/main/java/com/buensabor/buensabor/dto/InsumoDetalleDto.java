package com.buensabor.buensabor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsumoDetalleDto {
    private Long insumoId;
    private String denominacion;
    private String descripcion;
    private String codigo;
    private Integer cantidadNecesaria;
    private Integer stockMaximo;
    private BigDecimal precioCompra;
    private String unidadMedidaNombre;
    private String categoriaNombre;
}