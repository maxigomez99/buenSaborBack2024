package com.buensabor.buensabor.dto.promocion;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.entities.ImagenPromocion;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromocionDetalleDto extends BaseDto{

    private int cantidad;
    private ArticuloPromocionDto articuloManufacturadoDto;
    private ImagenPromocion imagenPromocion;

}