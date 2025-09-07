package com.buensabor.buensabor.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "MiSuperClaveJWT2025!$#@123";
    private final long EXPIRATION_TIME = 86400000; // 1 día en ms

    public String generateToken(String nombre, String apellido, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombre", nombre);
        claims.put("apellido", apellido);
        claims.put("rol", rol);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(nombre + " " + apellido)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

