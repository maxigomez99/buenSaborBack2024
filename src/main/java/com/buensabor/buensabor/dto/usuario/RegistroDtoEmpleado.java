package com.buensabor.buensabor.dto.usuario;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.enums.Rol;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistroDtoEmpleado extends BaseDto {
    private String password;
    private String nombre;
    private String apellido;
    private String Telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private Rol rol;
    private String imagen;
    private Sucursal sucursal;

}

