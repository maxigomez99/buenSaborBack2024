package com.buensabor.buensabor.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class ArticuloManufacturado extends Base{

    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;

}
