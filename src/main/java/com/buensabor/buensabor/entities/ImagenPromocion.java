package com.buensabor.buensabor.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class ImagenPromocion extends Base {

    private String denominacion;

    @ManyToOne
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;
}
