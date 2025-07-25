package com.buensabor.buensabor.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class UnidadMedida extends Base {

    private String denominacion;
    private String abreviatura;
    @OneToMany(mappedBy = "unidadMedida")
    @JsonIgnore
    private List<ArticuloInsumo> articulosInsumo;
}
