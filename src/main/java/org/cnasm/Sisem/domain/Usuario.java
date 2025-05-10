package org.cnasm.Sisem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    public String username;

    @Column(nullable = false)
    private String password;

    // Muchos usuarios pueden tener muchos roles, y viceversa
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarios_roles", // tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"), // FK que apunta a este usuario
            inverseJoinColumns = @JoinColumn(name = "rol_id") // FK que apunta a rol
    )
    @Column(nullable = false)
    private Set<Rol> roles = new HashSet<>();

    public List<String> getRolesParaAuthorities() {
        return roles.stream()
                .map(rol -> "ROLE_" + rol.getNombre().toUpperCase()) // convencion spring "ROLE_" habilita a usar @PreAuthorize("hasRole('ADMINISTRADOR')")
                .toList();
    }

    public List<String> getRolesString() {
        return roles.stream()
                .map(rol -> rol.getNombre().toUpperCase())
                .toList();
    }
}