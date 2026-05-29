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

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<ItemCarrito> items = new ArrayList<>();

    private String codigoCupon;

    private Integer descuentoAplicado;

    private Integer total;

}
