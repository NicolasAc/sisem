package org.cnasm.Sisem.service;


import org.cnasm.Sisem.domain.EstadoUsuario;
import org.cnasm.Sisem.domain.Rol;
import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.dto.UsuarioRequest;
import org.cnasm.Sisem.dto.UsuarioResponse;
import org.cnasm.Sisem.repository.UsuarioRepository;
import org.cnasm.Sisem.repository.RolRepository;
import java.util.Set;
import java.util.HashSet;

import org.cnasm.Sisem.security.JwtTokenUtil;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final RolRepository rolRepository;
    private final EmailService emailService;
    private final JwtTokenUtil jwtTokenUtil;

    public UsuarioService(UsuarioRepository repo, RolRepository rolRepository, EmailService emailService, JwtTokenUtil jwtTokenUtil) {
        this.repo = repo;
        this.rolRepository = rolRepository;
        this.emailService = emailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public List<UsuarioResponse> listarUsuarios() {
        return repo.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getUsername(),u.getNombre(),u.getApellido(),u.getNroCcjpu(),u.getEmail(),u.getFuncion(),u.getEstado(),null))
                .collect(Collectors.toList());
    }

    public UsuarioResponse crearUsuario(UsuarioRequest request) {

        if (repo.existsByUsername(request.getUsername())) {
            // 409 Conflict: ya existe un recurso con ese identificador
            throw new IllegalStateException("Ya existe un usuario con ese nombre de usuario");
        }

        if (repo.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Ya existe un usuario con ese correo electrónico");
        }

        if (repo.existsByNroCcjpu(request.getNroCcjpu())) {
            throw new IllegalStateException("Ya existe un usuario con ese N° CCJPU");
        }
        /*
        if (request.getNroCcjpu().length() < 7 || request.getNroCcjpu().length() > 8) {
            throw new IllegalArgumentException("El N° CCJPU debe tener 7 u 8 dígitos");
        }*/

        Set<Rol> roles = new HashSet<>(rolRepository.findByNombreIn(request.getRoles()));

        if (roles.size() != request.getRoles().size()) {
            throw new IllegalArgumentException("Uno o más roles no son válidos");
        }


        Usuario nuevo = new Usuario();
        nuevo.setUsername(request.getUsername());
        nuevo.setNombre(request.getNombre());
        nuevo.setApellido(request.getApellido());
        nuevo.setEmail(request.getEmail());
        nuevo.setFuncion(request.getFuncion());
        nuevo.setNroCcjpu(request.getNroCcjpu());
        nuevo.setEstado(EstadoUsuario.PENDIENTE); //
        nuevo.setRoles(roles);


        Usuario guardado = repo.save(nuevo);
        String token = jwtTokenUtil.generateActivationToken(nuevo.getUsername());
        emailService.enviarCorreoActivacion(nuevo.getEmail(), token);

        return new UsuarioResponse(
                guardado.getId(),
                guardado.getUsername(),
                guardado.getNombre(),
                guardado.getApellido(),
                guardado.getNroCcjpu(),
                guardado.getEmail(),
                guardado.getFuncion(),
                guardado.getEstado(),
                guardado.getRolesString()
        );
    }


}
