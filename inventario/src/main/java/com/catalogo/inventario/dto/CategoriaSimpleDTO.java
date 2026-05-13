package com.catalogo.inventario.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoriaSimpleDTO {

    private Integer idcategoria;
    private String nombre_categoria;

    private List<String> producto; 

}
