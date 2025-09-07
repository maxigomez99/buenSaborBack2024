package com.buensabor.buensabor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticulosParaVentaDto {
    private List<ArticuloManufacturadoSimpleDto> articulosManufacturados;
    private List<ArticuloInsumoSimpleDto> articulosInsumos;
}
