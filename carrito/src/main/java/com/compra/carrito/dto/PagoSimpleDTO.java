package com.compra.carrito.dto;

import lombok.Data;

@Data
public class PagoSimpleDTO {

    private Integer id;
    private Integer monto;
    private String estado;
}
