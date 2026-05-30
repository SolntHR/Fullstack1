package com.compra.carrito.dto;

import lombok.Data;

@Data
public class CuponDTO {

    private String codigoPromocional;
    private Integer descuento;
    private Boolean activo;
}
