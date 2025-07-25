package com.buensabor.buensabor.entities;

import com.buensabor.buensabor.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class Empleado extends Base {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private Rol rol;
    private String imagen;

    @OneToOne
    private UsuarioEmpleado usuarioEmpleado;

    @ManyToOne
    private Sucursal sucursal;
}
