package org.cnasm.Sisem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    // Obtener usuarios de este rol, si fuera necesario
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;
}
