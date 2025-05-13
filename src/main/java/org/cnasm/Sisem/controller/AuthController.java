package org.cnasm.Sisem.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.dto.AuthRequest;
import org.cnasm.Sisem.dto.AuthResponse;
import org.cnasm.Sisem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        Usuario usuario = authenticationService.buscarPorUsername(request.getUsername());

        if (token != null) {
            // ✅ 1. Crear cookie segura
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(false) // ⚠️ poné en true cuando uses HTTPS
                    .path("/")
                    .maxAge(86400) // 1 día
                    .sameSite("Strict")
                    .build();

            // ✅ 2. Agregar al header Set-Cookie
            response.addHeader("Set-Cookie", cookie.toString());

            // ✅ 3. Devolver solo info del usuario, sin el token
            return ResponseEntity.ok(new AuthResponse(null, usuario.getUsername(), usuario.getRolesString()));
        } else {
            return ResponseEntity.status(401).body("Usuario y/o contraseña inválida");
        }
/*
    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        Usuario usuario = authenticationService.buscarPorUsername(request.getUsername());
        if (token != null) {
            String usu=usuario.getUsername();
            return ResponseEntity.ok(new AuthResponse(token,usu,usuario.getRolesString()));
        } else {
            return ResponseEntity.status(401).body("Usuario y/o contraseña inválida");
        }
    }
*/
    }
}
