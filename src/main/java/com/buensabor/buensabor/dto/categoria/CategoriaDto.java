package com.buensabor.buensabor.dto.categoria;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.dto.sucursal.SucursalSimpleDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoriaDto extends BaseDto {

    private String denominacion;
    private String urlIcono;
    private Set<SucursalSimpleDto> sucursales = new HashSet<>();
    private Set<SubCategoriaDto> subCategoriaDtos = new HashSet<>();
}
