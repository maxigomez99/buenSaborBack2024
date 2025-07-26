package com.buensabor.buensabor.dto.compraProducto;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoDetalleDto extends BaseDto {
    private Integer cantidad;
    private Long pedidoId;
    private CompraProductoDto producto;
}

