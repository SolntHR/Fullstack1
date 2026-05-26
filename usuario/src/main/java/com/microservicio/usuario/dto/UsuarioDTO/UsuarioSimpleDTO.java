package com.microservicio.usuario.dto.UsuarioDTO;

import lombok.Data;

@Data
public class UsuarioSimpleDTO {

    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String nombreRol;

}
    