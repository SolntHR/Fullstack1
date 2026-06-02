package com.resena.resena.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResenaResponseDTO {
    
    private Integer idResena;
    private Integer idUsuario;
    private Integer idProducto;
    private Integer idCarrito;
    private Integer estrellas;
    private String comentario;
    private LocalDateTime fechaCreacion;
}
