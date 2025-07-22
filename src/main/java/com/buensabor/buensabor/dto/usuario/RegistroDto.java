package com.buensabor.buensabor.dto.usuario;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.entities.Cliente;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistroDto extends BaseDto {
    private String username;
    private String password;
    private Cliente cliente;
}