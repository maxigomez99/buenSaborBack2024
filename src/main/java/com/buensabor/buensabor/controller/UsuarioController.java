package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.usuario.*;
import com.buensabor.buensabor.entities.UsuarioCliente;
import com.buensabor.buensabor.entities.UsuarioEmpleado;
import com.buensabor.buensabor.errores.ApiError;
import com.buensabor.buensabor.service.impl.UsuarioService;
import com.buensabor.buensabor.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/cliente")
    public ResponseEntity<?> crearCliente(@RequestBody UsuarioCliente usuarioCliente) {
        try {
            return ResponseEntity.ok().body(usuarioService.crearUsuarioCliente(usuarioCliente));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/cliente/actualizarPassword/{clienteId}")
    public ResponseEntity<?> actualizarPasswordCliente(@PathVariable Long clienteId, @RequestBody CambioPasswordDto cambioPasswordDto) {
        try {

            usuarioService.actualizarPasswordCliente(clienteId, cambioPasswordDto.getNuevaPassword());
            return ResponseEntity.ok().body("Contraseña actualizada con éxito");
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/cliente/login")
    public ResponseEntity<?> loginCliente(@RequestParam String username, @RequestParam String password) {
        try {
            return ResponseEntity.ok().body(usuarioService.loginCliente(username, password));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<?> crearEmpleado(@RequestBody UsuarioEmpleado usuarioEmpleado) {
        try {
            return ResponseEntity.ok().body(usuarioService.crearUsuarioEmpleado(usuarioEmpleado));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @GetMapping("/empleado/login")
    public ResponseEntity<?> loginEmpleado(@RequestParam String username, @RequestParam String password) {
        try {
            return ResponseEntity.ok().body(usuarioService.loginEmpleado(username, password));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto userDto) {
        try {
            UserResponseDto userResponse = usuarioService.login(userDto.getUsername(), userDto.getPassword());

            // Generar JWT
            String token = jwtUtil.generateToken(
                userResponse.getUsername(),
                userResponse.getRol(),
                userResponse.getNombre(),
                userResponse.getApellido()
            );

            // Crear respuesta con token y datos del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", userResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/registro/usuario-cliente")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDto registroDto) {
        try {
            UsuarioCliente usuario = usuarioService.registrarUsuario(registroDto);
            String nombre = registroDto.getCliente().getNombre();
            String apellido = registroDto.getCliente().getApellido();
            String username = registroDto.getUsername(); // Asumiendo que existe este campo
            String rol = registroDto.getCliente().getRol().name();

            // Generar JWT con parámetros correctos
            String token = jwtUtil.generateToken(username, rol, nombre, apellido);

            // Crear respuesta con token y datos del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/registro/usuario-empleado")
    //@PreAuthorize(" hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> registrarEmpleado(@RequestBody RegistroDtoEmpleado registroDtoEmpleado) {
        try {
            UsuarioEmpleado usuario = usuarioService.registrarEmpleado(registroDtoEmpleado);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }


    @PostMapping("/cambiar-password-empleado")
    public ResponseEntity<?> cambiarPasswordEmpleado(@RequestBody CambioPasswordDto cambioPasswordDto) {
        try {
            boolean resultado = usuarioService.cambiarPasswordEmpleado(cambioPasswordDto.getUsername(), cambioPasswordDto.getPasswordActual(), cambioPasswordDto.getNuevaPassword());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/actualizar-password-empleado")
    public ResponseEntity<?> actualizarPasswordEmpleado(@RequestBody CambioPasswordDto cambioPasswordDto) {
        try {
            boolean resultado = usuarioService.actualizarPasswordEmpleado(cambioPasswordDto.getId(), cambioPasswordDto.getNuevaPassword());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/cambiar-password-cliente")
    public ResponseEntity<?> cambiarPasswordCliente(@RequestBody CambioPasswordDto cambioPasswordDto) {
        try {
            boolean resultado = usuarioService.cambiarPasswordCliente(cambioPasswordDto.getUsername(), cambioPasswordDto.getPasswordActual(), cambioPasswordDto.getNuevaPassword());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/google-login")
    public ResponseEntity<?> buscarUsuarioPorEmail(@RequestParam String email) {
        try {
            UserResponseDto usuario = usuarioService.buscarUsuarioPorEmail(email);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }


}
