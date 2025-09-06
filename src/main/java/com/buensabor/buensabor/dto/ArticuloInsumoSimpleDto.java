package com.buensabor.buensabor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloInsumoSimpleDto {
    private Long id;
    private String denominacion;
    private String descripcion;
    private String codigo;
    private Double precioVenta;
    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Boolean esParaElaborar;
    private String categoriaNombre;
    private String unidadMedidaNombre;
}
