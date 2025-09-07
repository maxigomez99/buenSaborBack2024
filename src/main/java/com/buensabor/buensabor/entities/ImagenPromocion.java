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
@Builder
//@Audited
public class ImagenPromocion extends Base{
    private String url;
}
