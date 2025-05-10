package org.cnasm.Sisem.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Paso 1: Leer el encabezado Authorization
        final String authHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        // Paso 2: Verificar que el header tenga formato Bearer y extraer el token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Quita el "Bearer "

            try {
                // Paso 3: Parsear el token y extraer los claims
                Claims claims = jwtTokenUtil.parseClaims(jwtToken);
                username = claims.getSubject(); // Este es el username

                // Paso 4: Leer los roles desde el claim "roles"
                @SuppressWarnings("unchecked")
                List<String> roles = claims.get("roles", List.class);

                // Paso 5: Convertir roles en authorities reconocidas por Spring
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // Paso 6: Construir el objeto Authentication con username y roles
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                // Paso 7: Guardar el Authentication en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                System.out.println("Token inválido o mal formado: " + e.getMessage());
            }
        }

        // Paso 8: Continuar con el resto de la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
