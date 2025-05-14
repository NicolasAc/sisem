package org.cnasm.Sisem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String nroCcjpu;
    private String email;
    private String funcion;
    private boolean activo;
}