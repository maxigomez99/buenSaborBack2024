package com.buensabor.buensabor.dto.articuloInsumo;

import lombok.Data;

@Data
public class ArticuloInsumoTablaDto {
    private Long id;
    private String imagen;
    private String imagenPrincipal;
    private String codigo;
    private String denominacion;
    private String descripcion;
    private Object categoria;
    private String categoriaDenominacion;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Object unidadMedida;
    private String unidadMedidaDenominacion;
    private Boolean esParaElaborar;
    private Object sucursal;
    private Boolean estado;
    private Boolean eliminado; // Cambiado de Integer a Boolean para compatibilidad
    private String fechaCreacion;
    private String fechaActualizacion;
}
