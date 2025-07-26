package com.buensabor.buensabor.dto.articuloManufacturado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloManufacturadoCantidadDto {
    private Long articuloManufacturadoId;
    private int cantidadMaximaDisponible;

}
