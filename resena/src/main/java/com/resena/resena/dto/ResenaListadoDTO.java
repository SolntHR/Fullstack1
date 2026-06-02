package com.resena.resena.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResenaListadoDTO {
    
    private Integer idResena;
    private Integer idUsuario;
    private Integer idProducto;
    private Integer idPago;
    private Integer estrellas;
    private String comentario;
    private LocalDateTime fechaCreacion;
}
