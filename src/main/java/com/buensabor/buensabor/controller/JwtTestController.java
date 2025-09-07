package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
public class JwtTestController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        try {
            boolean isValid = jwtUtil.validateToken(token);

            Map<String, Object> response = new HashMap<>();
            response.put("valid", isValid);

            if (isValid) {
                response.put("username", jwtUtil.getUsernameFromToken(token));
                response.put("rol", jwtUtil.getRolFromToken(token));
                response.put("nombre", jwtUtil.getNombreFromToken(token));
                response.put("apellido", jwtUtil.getApellidoFromToken(token));
                response.put("expired", jwtUtil.isTokenExpired(token));
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("valid", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/test-generate")
    public ResponseEntity<?> testGenerateToken(@RequestBody Map<String, String> data) {
        try {
            String token = jwtUtil.generateToken(
                    data.get("username"),
                    data.get("rol"),
                    data.get("nombre"),
                    data.get("apellido")
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token no proporcionado o formato incorrecto");
            }

            String token = authHeader.substring(7); // Remover "Bearer "

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Token inválido o expirado");
            }

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", jwtUtil.getUsernameFromToken(token));
            userInfo.put("rol", jwtUtil.getRolFromToken(token));
            userInfo.put("nombre", jwtUtil.getNombreFromToken(token));
            userInfo.put("apellido", jwtUtil.getApellidoFromToken(token));
            userInfo.put("message", "Acceso autorizado correctamente");

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error de autenticación: " + e.getMessage());
        }
    }
}
