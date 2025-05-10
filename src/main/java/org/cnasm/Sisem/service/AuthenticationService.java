package org.cnasm.Sisem.service;

import org.cnasm.Sisem.domain.Usuario;
import org.cnasm.Sisem.repository.UsuarioRepository;
import org.cnasm.Sisem.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private boolean coincide;

    public String authenticate(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            //Agregar a Usuario una funcion que pase sus roles con formato requerido por Spring
            List<String> roles = usuario.getRoles().stream()
                    .map(r -> "ROLE_" + r.getNombre().toUpperCase())
                    .toList();
            System.out.println("Autenticacion exitosa: " + username);
            return jwtTokenUtil.generateToken(username,roles);
        }

        System.out.println("Autenticacion fallida para: " + username);
        return null;
    }
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
