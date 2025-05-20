package org.cnasm.Sisem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cnasm.Sisem.domain.EstadoUsuario;

import java.util.List;

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
    private EstadoUsuario Estado;
    private List<String> roles;
}