package com.catalogo.inventario.dto;

import lombok.Data;

@Data
public class ProductoListadoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private Integer precioProducto;
    private Integer stockProducto;
    private String nombreCategoria;
}
