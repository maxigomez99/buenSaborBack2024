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
public class Domicilio extends Base {
    private String calle;
    private Integer numero;
    private Integer cp;
    private String piso;
    private String numeroDepto  ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

//    @ManyToMany(mappedBy = "domicilios")
//    private List<Cliente> clientes;
}
