package com.buensabor.buensabor.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//@Audited
public class ImagenArticulo extends Base{

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String url;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    @JsonBackReference
    private Articulo articulo;
}