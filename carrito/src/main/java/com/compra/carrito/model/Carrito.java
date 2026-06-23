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
    @Positive(message = "El id del usuario debe ser mayor a 0")
    @Column(nullable = false)
    private Integer idUsuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    @Size(max = 50, message = "El código de cupón no puede superar los 50 caracteres")
    private String codigoCupon;

    @Min(value = 0, message = "El descuento no puede ser negativo")
    private Integer descuentoAplicado;

    @NotNull(message = "Debe indicar el total del carrito")
    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer total;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|PAGADO|ABANDONADO|CANCELADO|COMPLETADO", message = "Estado de carrito no válido")
    @Column(nullable = false, length = 20)
    private String estado;

}
