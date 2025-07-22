package com.buensabor.buensabor.service;

import com.buensabor.buensabor.dto.usuario.UserResponseDto;
import com.buensabor.buensabor.entities.UsuarioCliente;
import com.buensabor.buensabor.entities.UsuarioEmpleado;
import org.apache.http.auth.InvalidCredentialsException;

public interface IUsuarioService {
    public UsuarioCliente crearUsuarioCliente(UsuarioCliente usuario) throws Exception;
    public UserResponseDto loginCliente(String username, String password) throws InvalidCredentialsException;



    public UsuarioEmpleado crearUsuarioEmpleado(UsuarioEmpleado usuario) throws Exception;
    public UserResponseDto loginEmpleado(String username, String password) throws InvalidCredentialsException;
}
