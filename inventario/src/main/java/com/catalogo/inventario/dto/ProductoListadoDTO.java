package com.catalogo.inventario.dto;

import lombok.Data;

@Data
public class ProductoListadoDTO {
    private Integer idproducto;
    private String nombre_producto;
    private Integer precio_producto;
    private Integer stock_producto;
    private String nombre_categoria;
}
