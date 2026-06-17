package com.catalogo.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {

    //IDCATEGORIA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;
    //NOMBRE_CATEGORIA
    @NotBlank(message = "El nombre del producto no puede esta vacio")
    @Column(nullable = false, length = 40, unique = true)
    private String nombreCategoria;
}
