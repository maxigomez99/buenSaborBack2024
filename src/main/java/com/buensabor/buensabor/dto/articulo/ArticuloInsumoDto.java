package com.buensabor.buensabor.dto.articulo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticuloInsumoDto {
    private Long id;
    private String denominacion;
    private String descripcion;
    private String codigo;
    private Double precioVenta;
    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Boolean esParaElaborar;
    private Integer stockMinimo;

    // Relaciones como IDs
    private Long unidadMedidaId;
    private Long categoriaId;
    private Long sucursalId;

    // Lista de imágenes en base64
    private List<String> imagenes;
}
