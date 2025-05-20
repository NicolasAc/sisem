package org.cnasm.Sisem.service;

import org.cnasm.Sisem.domain.EstadoUsuario;
import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.repository.UsuarioRepository;
import org.cnasm.Sisem.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String authenticate(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        if (usuario.getEstado() == EstadoUsuario.PENDIENTE) {
            throw new DisabledException("El usuario aún no activó su cuenta.");
        }

        if (usuario.getEstado() == EstadoUsuario.DESHABILITADO) {
            throw new DisabledException("El usuario está deshabilitado.");
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        List<String> roles = usuario.getRoles().stream()
                .map(r -> "ROLE_" + r.getNombre().toUpperCase())
                .toList();

        return jwtTokenUtil.generateToken(username, roles);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
