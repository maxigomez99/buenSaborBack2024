package com.buensabor.buensabor.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class Sucursal extends Base {
  private String nombre;
  private String horarioApertura;
    private String horarioCierre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Empresa empresa;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Domicilio domicilio;

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String imagen;

}
