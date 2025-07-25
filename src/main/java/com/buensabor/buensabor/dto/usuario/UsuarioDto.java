package com.buensabor.buensabor.dto.usuario;

import com.buensabor.buensabor.dto.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsuarioDto extends BaseDto {
    private String username;
    private String password;
}
