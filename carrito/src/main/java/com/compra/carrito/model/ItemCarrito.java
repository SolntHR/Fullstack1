package com.compra.carrito.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemCarrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Debe ingresar la ID del producto")
    private Integer idProducto;

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @NotNull(message = "Debe ingresar una cantidad mayor a 0")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    @JsonIgnore
    private Carrito carrito;

    @Positive(message = "El precio debe ser mayor a 0")
    @NotNull(message = "Debe ingresar un precio")
    private Integer precio;

    public int getSubTotal(){
        return cantidad * precio;
    }
}
