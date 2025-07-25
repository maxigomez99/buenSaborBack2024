package com.buensabor.buensabor.dto.empresa;

import lombok.Data;

@Data
public class EmpresaDto {
    private String nombre;
    private String razonSocial;
    private Long cuil;
    private String imagen; // Base64 de la imagen
}