package org.cnasm.Sisem.controller;


import jakarta.validation.Valid;
import org.cnasm.Sisem.dto.UsuarioRequest;
import org.cnasm.Sisem.dto.UsuarioResponse;
import org.cnasm.Sisem.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ADMINISTRATIVO')")
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioRequest request) {
        try {
            UsuarioResponse nuevoUsuario = usuarioService.crearUsuario(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevoUsuario);
        } catch (IllegalStateException e) {
            // Ya existe el usuario (username o email duplicado)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // o body(e.getMessage()) si querés mostrarlo
        } catch (IllegalArgumentException e) {
            // Roles inválidos
            return ResponseEntity.badRequest().body(null);
        }
    }

}
