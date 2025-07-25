package com.buensabor.buensabor.dto.categoria;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class SubCategoriaConEmpresaDto extends BaseDto{
    private String denominacion;
    private String urlIcono;
    private Long idCategoriaPadre;
    private Long idEmpresaCategoriaPadre;
    private Set<SubCategoriaConEmpresaDto> subSubCategoriaDtos = new HashSet<>();
}
