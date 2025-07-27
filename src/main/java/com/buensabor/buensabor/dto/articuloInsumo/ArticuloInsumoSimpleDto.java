package com.buensabor.buensabor.dto.articuloInsumo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
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
    private String unidadMedidaDenominacion;
    private String categoriaDenominacion;
    private String imagenPrincipal; // Solo una imagen principal en lugar de todo el set
}
