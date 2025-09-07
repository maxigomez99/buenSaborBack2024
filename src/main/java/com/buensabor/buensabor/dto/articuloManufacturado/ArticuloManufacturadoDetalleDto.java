package com.buensabor.buensabor.dto.articuloManufacturado;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.dto.articuloInsumo.ArticuloInsumoDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloManufacturadoDetalleDto extends BaseDto {

    private ArticuloInsumoDto articuloInsumoDto;
    private Integer cantidad;
    private ArticuloManufacturadoDto articuloManufacturadoDto;
}

