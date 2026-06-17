package com.compra.carrito.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrito;

    @NotNull(message = "Debe ingresar el id del usuario")
    @Column(nullable = false)
    private Integer idUsuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    @Column(length = 50)
    private String codigoCupon;

    @Min(value = 0, message = "El descuento no puede ser negativo")
    private Integer descuentoAplicado;

    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer total;

    @Column(nullable = false, length = 20)
    private String estado;

}
