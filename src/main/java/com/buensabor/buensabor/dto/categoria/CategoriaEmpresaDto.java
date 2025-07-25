package com.buensabor.buensabor.dto.categoria;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoriaEmpresaDto extends BaseDto {

    private String denominacion;
    private boolean eliminado;
    private Long empresaId;
    private String urlIcono;

    private Set<SubCategoriaConEmpresaDto> subCategoriaDtos = new HashSet<>();
}
