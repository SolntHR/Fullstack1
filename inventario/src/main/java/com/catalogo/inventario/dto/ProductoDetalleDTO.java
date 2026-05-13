package com.catalogo.inventario.dto;

import lombok.Data;

@Data
public class ProductoDetalleDTO {
    private Integer idproducto;
    private String nombre_producto;
    private String descripcion_producto;
    private Integer precio_producto;
    private Integer stock_producto;
    private CategoriaListadoDTO categoria;
}
