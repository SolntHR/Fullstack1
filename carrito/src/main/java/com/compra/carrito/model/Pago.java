package com.compra.carrito.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El carrito es obligatorio")
    private Integer idCarrito;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @NotNull(message = "El monto es obligatorio")
    private Integer monto;

    private String estado;

    private LocalDateTime fechaCreacion;
}
