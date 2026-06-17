package com.catalogo.inventario.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoriaSimpleDTO {

    private Integer idCategoria;
    private String nombreCategoria;

    private List<String> producto; 

}
