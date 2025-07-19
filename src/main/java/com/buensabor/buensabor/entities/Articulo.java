package com.buensabor.buensabor.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public abstract class Articulo extends Base {

    protected String denominacion;
    protected Double precioVenta;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    protected Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id")
    protected UnidadMedida unidadMedida;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    protected List<ImagenArticulo> imagenes;

    // Constructor con id para facilitar la creación de entidades con un id específico
    public Articulo(Long id) {
        super();
        this.setId(id);
    }
}
