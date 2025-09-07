package com.buensabor.buensabor.dto.usuario;

import com.buensabor.buensabor.enums.Rol;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponseDto  {
    private String username;
    private Rol rol;
    private Long idUsuario;
    private long idSucursal;
    private long idEmpresa;
    private long idCliente;
    private Long idEmpleado;
    private String nombre;
    private String apellido;
}
