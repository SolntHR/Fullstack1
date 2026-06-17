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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoria no puede estar vacio")
    @Size(min = 3, max = 40, message = "El nombre de la categoría debe tener entre 3 y 40 caracteres")
    @Column(nullable = false, length = 40, unique = true)
    private String nombreCategoria;
}
