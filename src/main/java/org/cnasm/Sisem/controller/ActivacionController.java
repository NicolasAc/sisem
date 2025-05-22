package org.cnasm.Sisem.controller;

import org.cnasm.Sisem.domain.EstadoUsuario;
import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.dto.ActivacionRequest;
import org.cnasm.Sisem.repository.UsuarioRepository;
import org.cnasm.Sisem.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuenta")
public class ActivacionController {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public ActivacionController(UsuarioRepository usuarioRepository, JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/activar")
    public ResponseEntity<String> activarCuenta(@RequestParam String token) {
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);

            if (!jwtTokenUtil.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
            }

            Usuario usuario = usuarioRepository.findByUsername(username);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            usuario.setEstado(EstadoUsuario.ACTIVO); // O el estado que uses
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Cuenta activada correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido.");
        }
    }

    @PostMapping("/activar")
    public ResponseEntity<?> activarCuenta(@RequestBody ActivacionRequest request) {
        String token = request.getToken();
        String nuevaPassword = request.getPassword();

        if (token == null || nuevaPassword == null || nuevaPassword.isBlank()) {
            return ResponseEntity.badRequest().body("Faltan datos");
        }

        String username;
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
            if (!jwtTokenUtil.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token no válido");
        }

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        if (usuario.getEstado() != EstadoUsuario.PENDIENTE) {
            return ResponseEntity.badRequest().body("El usuario ya está activado");
        }

        /*usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setEstado(EstadoUsuario.ACTIVO);*/
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Cuenta activada correctamente");
    }
}
