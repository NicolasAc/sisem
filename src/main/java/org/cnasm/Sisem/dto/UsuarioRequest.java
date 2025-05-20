package org.cnasm.Sisem.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[\\p{L}]+(\\s[\\p{L}]+)*$", message = "El nombre no puede contener números ni símbolos")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[\\p{L}]+(\\s[\\p{L}]+)*$", message = "El apellido no puede contener números ni símbolos")
    private String apellido;

    @NotBlank(message = "NroCcjpu es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "Debe contener solo números (7 u 8 dígitos)")
    private String nroCcjpu;

    @NotBlank(message = "La función es obligatoria")
    private String funcion;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotEmpty(message = "Debe asignar al menos un rol")
    private List<String> roles;
}