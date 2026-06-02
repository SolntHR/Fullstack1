package com.catalogo.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="producto")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Producto {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @NotBlank(message = "El nombre del producto no puede esta vacio")
    @Column(nullable = false, length = 100)
    private String nombreProducto;
    
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Column(nullable = false, length = 100)
    private String descripcion_producto;
    
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    @NotNull(message = "Debe ingresar un precio")
    private Integer precio;

    @Min(value = 0, message = "El stock debe ser mayor a 0")
    @NotNull(message = "Debe ingresar la cantidad de stock")
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", nullable = false) 
    @NotNull(message = "El producto debe tener una categoría válida")
    private Categoria categoria;

}
