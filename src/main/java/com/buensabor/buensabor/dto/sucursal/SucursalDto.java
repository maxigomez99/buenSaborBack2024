package com.buensabor.buensabor.dto.sucursal;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SucursalDto extends BaseDto {
    private String nombre;
    private String horarioApertura;
    private String horarioCierre;
    private String calle;
    private String numero;
    private String cp;
    private String piso;
    private String nroDepto;
    private String localidad;
    private String provincia;
    private String pais; // Cambiado a minúscula para seguir convención de nombres
    private Long idEmpresa; // Cambiado a Long para que coincida con el tipo de dato en la entidad Empresa
    private String imagen;
}
