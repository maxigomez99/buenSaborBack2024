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
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMinimo;
    private Boolean esParaElaborar;


    @OneToMany(mappedBy = "articuloInsumo")
    private List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles;
}