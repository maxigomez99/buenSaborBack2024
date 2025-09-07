package com.buensabor.buensabor.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class PedidoDetalle extends Base{
    private Integer cantidad;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Pedido pedido;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Articulo articulo;
}
