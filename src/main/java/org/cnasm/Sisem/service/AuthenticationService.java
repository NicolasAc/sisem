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

    public String authenticate(String username, String password,String ipCliente) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }


        if (usuario.isCuentaBloqueada()) {
            throw new DisabledException("La cuenta está bloqueada.");
        }

        if (usuario.getEstado() == EstadoUsuario.PENDIENTE) {
            throw new DisabledException("El usuario aún no activó su cuenta.");
        }

        if (usuario.getEstado() == EstadoUsuario.DESHABILITADO) {
            throw new DisabledException("El usuario está deshabilitado.");
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);
            usuario.setUltimoIntentoFallido(java.time.LocalDateTime.now());


            if (intentos >= 7) {
                usuario.setCuentaBloqueada(true);
                usuario.setFechaBloqueo(java.time.LocalDateTime.now());
                usuario.setIpBloqueo(ipCliente);

                usuarioRepository.save(usuario);
                throw new BadCredentialsException("Cuenta bloqueada tras ingresar contraseña incorrecta en 7 intentos.. ");

            }

            usuarioRepository.save(usuario);
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        // Login exitoso: resetear intentos
        usuario.setIntentosFallidos(0);
        usuario.setCuentaBloqueada(false);
        usuario.setFechaBloqueo(null);
        usuario.setIpBloqueo(null);
        usuarioRepository.save(usuario);

        List<String> roles = usuario.getRoles().stream()
                .map(r -> "ROLE_" + r.getNombre().toUpperCase())
                .toList();

        return jwtTokenUtil.generateToken(username, roles);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
