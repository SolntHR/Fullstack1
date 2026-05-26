package com.compra.carrito.dto;

import lombok.Data;

@Data
public class ItemDTO {

    private Integer idproducto;
    private String nombreProducto;
    private Integer cantidad;
    private Integer precio;
    
}
