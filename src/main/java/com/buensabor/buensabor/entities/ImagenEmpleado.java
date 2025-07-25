package com.buensabor.buensabor.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder

public class ImagenEmpleado extends Base{

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagen;
}
