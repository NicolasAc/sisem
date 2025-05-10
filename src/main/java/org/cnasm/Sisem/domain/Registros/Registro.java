package org.cnasm.Sisem.domain.Registros;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaEvento;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false)
    private Long personaId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long programaId;

    @Column(nullable = false)
    private Long grupoId;

    public abstract void procesar();

    public abstract void validar();

    protected void validarBase() {
        if (fechaEvento == null) {
            throw new ValidacionException("La fecha del Registro es obligatoria.");
        }

        if (fechaGeneracion == null) {
            throw new ValidacionException("La fecha de generación es obligatoria.");
        }

        if (personaId == null) {
            throw new ValidacionException("Debe asociar el registro a una persona.");
        }

        if (usuarioId == null) {
            throw new ValidacionException("Debe asociar el registro a un usuario.");
        }

        if (programaId == null) {
            throw new ValidacionException("Debe asociar el registro a un programa.");
        }

        if (grupoId == null) {
            throw new ValidacionException("Debe asociar el registro a un grupo.");
        }
    }
}
