package com.resena.resena.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResenaCrearDTO {
    
    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El idProducto es obligatorio")
    private Integer idProducto;

    @NotNull(message = "El idPedido es obligatorio")
    private Integer idCarrito;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer estrellas;

    @Size(max = 500, message = "El comentario no puede superar los 1000 caracteres")
    private String comentario;
}
