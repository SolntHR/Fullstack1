package com.envio.despacho.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {

    private Integer idCarrito;
    private Integer idUsuario;
    private Integer total;
}
