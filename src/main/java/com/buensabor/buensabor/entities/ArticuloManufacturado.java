package com.buensabor.buensabor.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class ArticuloManufacturado extends Articulo {

    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;

    @OneToMany(mappedBy = "articuloManufacturado")
    private List<ArticuloManufacturadoDetalle> detalles;

    @OneToMany(mappedBy = "articuloManufacturado")
    private List<PromocionDetalle> promocionDetalles;
}
