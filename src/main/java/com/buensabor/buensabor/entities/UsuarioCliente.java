package com.buensabor.buensabor.entities;

import jakarta.persistence.Column;
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

public class UsuarioCliente extends Base{
    @Column(unique = true)
    private String username;
    private String password;

}
