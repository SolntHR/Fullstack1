package com.catalogo.inventario.dto;

import lombok.Data;

@Data
public class ProductoListadoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private Integer precio_producto;
    private Integer stock_producto;
    private String nombre_categoria;
}
