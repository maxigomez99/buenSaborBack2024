package com.buensabor.buensabor.dto.pedido;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoDetalleDto extends BaseDto {
     private ArticuloManufacturadoDto articulo;
    private Integer cantidad;

}