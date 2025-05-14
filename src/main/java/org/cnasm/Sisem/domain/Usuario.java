package org.cnasm.Sisem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // nombre de usuario para login

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "nro_ccjpu", length = 7)
    private String nroCcjpu;

    @Column(nullable = false)
    private String funcion;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    public List<String> getRolesParaAuthorities() {
        return roles.stream()
                .map(rol -> "ROLE_" + rol.getNombre().toUpperCase())
                .toList();
    }

    public List<String> getRolesString() {
        return roles.stream()
                .map(rol -> rol.getNombre().toUpperCase())
                .toList();
    }
}
