package org.cnasm.Sisem.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.dto.AuthRequest;
import org.cnasm.Sisem.dto.UsuarioResponse;
import org.cnasm.Sisem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
            Usuario usuario = authenticationService.buscarPorUsername(request.getUsername());
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(false) // CAMBIAR A TRUE en pord
                    .path("/")
                    .maxAge(86400)
                    .sameSite("Strict")
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());

            // ✅ 4. Devolver info del usuario
            return ResponseEntity.ok(new UsuarioResponse(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getNroCcjpu(),
                    usuario.getEmail(),
                    usuario.getFuncion(),
                    usuario.getEstado(),
                    usuario.getRolesString()
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
