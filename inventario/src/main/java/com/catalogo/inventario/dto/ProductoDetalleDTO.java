package com.catalogo.inventario.dto;

import lombok.Data;

@Data
public class ProductoDetalleDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String descripcion_producto;
    private Integer precio_producto;
    private Integer stock_producto;
    private CategoriaListadoDTO categoria;
}
