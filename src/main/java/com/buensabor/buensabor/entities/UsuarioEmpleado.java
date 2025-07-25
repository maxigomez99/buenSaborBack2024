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

public class UsuarioEmpleado extends Base {
    private String username;
    private String password;
    private String auth0Id;

    private Empleado empleado;
}
