package org.cnasm.Sisem.service;


import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.dto.UsuarioResponse;
import org.cnasm.Sisem.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<UsuarioResponse> listarUsuarios() {
        return repo.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getUsername(),u.getNombre(),u.getApellido(),u.getNroCcjpu(),u.getEmail(),u.getFuncion(),u.isActivo()))
                .collect(Collectors.toList());
    }
}
