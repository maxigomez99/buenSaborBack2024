package com.buensabor.buensabor.dto.pedido;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.entities.Cliente;
import com.buensabor.buensabor.enums.Estado;
import com.buensabor.buensabor.enums.FormaPago;
import com.buensabor.buensabor.enums.TipoEnvio;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoDto extends BaseDto {
    private Long id;
    private LocalTime hora;
    private Double total;
    private Double totalCostoProduccion;
    private Estado estado;
    private FormaPago formaPago;
    private TipoEnvio tipoEnvio;
    private LocalDate fechaPedido;
    private String preferenceMPId;
    private Cliente cliente;
    private List<PedidoDetalleDto> pedidoDetalleDto;
    private DomicilioDto domicilioDto = new DomicilioDto();


}
