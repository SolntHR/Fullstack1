package com.resena.resena.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResenaDetalleDTO {
    private Integer idResena;
    private Integer idUsuario;
    private Integer idProducto;
    private Integer idItemCarrito;
    private Integer estrellas;
    private String comentario;
    private LocalDateTime fechaCreacion;
}
