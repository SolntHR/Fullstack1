package com.compra.carrito.dto;

import lombok.Data;

@Data
public class ItemDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Integer stockProducto;
    private Integer cantidad;
    private Integer precio;
    
}
