package com.envio.despacho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;

    @NotNull(message = "El ID del carrito es obligatorio")
    @Positive(message = "El ID del carrito debe ser mayor a 0")
    private Integer idCarrito;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(min = 3, max = 100, message = "La comuna debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String comuna;

    @NotBlank(message = "La región es obligatoria")
    @Size(min = 3, max = 100, message = "La región debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String region;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "PENDIENTE|PREPARANDO|DESPACHADO|EN_RUTA|ENTREGADO|CANCELADO|DEVUELTO",
             message = "Estado no válido")
    @Column(nullable = false, length = 30)
    private String estado;
}
