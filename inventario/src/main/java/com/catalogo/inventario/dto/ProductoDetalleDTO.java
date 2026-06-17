package com.catalogo.inventario.dto;

import lombok.Data;

@Data
public class ProductoDetalleDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Integer precioProducto;
    private Integer stockProducto;
    private CategoriaListadoDTO categoria;
}
