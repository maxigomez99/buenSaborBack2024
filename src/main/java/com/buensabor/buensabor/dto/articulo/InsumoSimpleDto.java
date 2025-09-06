package com.buensabor.buensabor.dto.articulo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsumoSimpleDto {
    private Long id;
    private String imagen;
    private String imagenPrincipal;
    private String codigo;
    private String denominacion;
    private String descripcion;
    private Long categoria;
    private String categoriaDenominacion;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Long unidadMedida;
    private String unidadMedidaDenominacion;
    private Boolean esParaElaborar;
    private Object sucursal;
    private Object estado;
    private Boolean eliminado;
    private String fechaCreacion;
    private String fechaActualizacion;
}
