package com.catalogo.inventario.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoriaDetalleDTO {
    private Integer idCategoria;
    private String nombreCategoria;

    private List<ProductoDetalleDTO> productos;
    
}
