package com.buensabor.buensabor.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "tipo_promocion")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class TipoPromocion extends Base {

    private String denominacion;
    private String descripcion;

    @OneToMany(mappedBy = "tipoPromocion")
    private List<Promocion> promociones;
}