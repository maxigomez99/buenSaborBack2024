package com.buensabor.buensabor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
public class Categoria  extends Base {


    private String denominacion;

    @ManyToOne  ()
    @JoinColumn(name ="categoriaPadreId")
    private Categoria categoriaPadre;
    @ManyToOne ()
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
