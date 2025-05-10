package org.cnasm.Sisem.controller;


import org.cnasm.Sisem.dto.UsuarioResponse;
import org.cnasm.Sisem.service.UsuarioService;
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
}
