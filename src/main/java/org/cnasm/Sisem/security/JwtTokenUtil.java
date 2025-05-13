package org.cnasm.Sisem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtil {

    // Clave secreta segura (64 caracteres = 512 bits)
    private static final String SECRET_KEY = "G9df3kZP8m2XLrQ0BnA5uYvCwTz1EeHyUkLoIxMsQpN7RbVcJaKhZdFgWtSyXqM1";

    // Tiempo de expiración del token: 1 día
    private static final long EXPIRATION_TIME = 1800000L; // 30 Minutos de vigencia del token

    // Generar el token
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // 👈 importante
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Extraer username del token
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Validar si el token es válido y pertenece al usuario
    public boolean validateToken(String token, String username) {
        String extractedUsername = getUsernameFromToken(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    // Verificar si el token expiró
    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    // Extraer claims del token
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener key a partir del string seguro
    public Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
