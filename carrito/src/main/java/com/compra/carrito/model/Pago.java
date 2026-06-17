package com.compra.carrito.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @NotNull(message = "El carrito es obligatorio")
    @Positive(message = "El ID del carrito debe ser mayor a 0")
    private Integer idCarrito;

    @NotBlank(message = "El metodo de pago es obligatorio")
    @Size(min = 3, max = 30, message = "El método de pago debe tener entre 3 y 30 caracteres")
    @Column(nullable = false, length = 30)
    private String metodoPago;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    @Column(nullable = false)
    private Integer monto;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern( regexp = "PENDIENTE|APROBADO|RECHAZADO|ANULADO", message = "Estado de pago no válido")
    @Column(nullable = false, length = 20)
    private String estado;

    @NotNull(message = "La fecha de creación es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
}
