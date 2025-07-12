package com.buensabor.buensabor.entities;

import com.buensabor.buensabor.enums.Estado;
import com.buensabor.buensabor.enums.FormaPago;
import com.buensabor.buensabor.enums.TipoEnvio;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder

public class Pedido extends Base {
    private LocalTime hora;
    private Double total;
    private Double totalCostoProduccion;
    private Estado estado;
    private FormaPago formaPago;
    private TipoEnvio tipoEnvio;
    private LocalDate fechaPedido;
    private String preferenceMPId;

    @ManyToOne
    private Sucursal sucursal;
    @ManyToOne(cascade = CascadeType.ALL)
    private Domicilio domicilio;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoDetalle> pedidoDetalle;
    @OneToOne
    private Factura factura;
}
