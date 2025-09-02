package com.buensabor.buensabor.dto.articuloManufacturado;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloManufacturadoTablaDto extends BaseDto {
    private String codigo;
    private String denominacion;
    private String imagen;
    private Double precioVenta;
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
}
