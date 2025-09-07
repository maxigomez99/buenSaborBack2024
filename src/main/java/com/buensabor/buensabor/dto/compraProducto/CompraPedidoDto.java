package com.buensabor.buensabor.dto.compraProducto;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.entities.*;
import com.buensabor.buensabor.enums.Estado;
import com.buensabor.buensabor.enums.FormaPago;
import com.buensabor.buensabor.enums.TipoEnvio;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompraPedidoDto extends BaseDto {
    private String hora;
    private Double total;
    private Double totalCosto;
    private String fechaPedido;
    private String preferenceMPId;
    private Sucursal sucursal;
    private Domicilio domicilio;
    private Cliente cliente;
    private List<PedidoDetalleDto> pedidoDetalle;
    private Factura factura;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private int tiempoEspera;
    private Long sucursalId;

}