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
    private Integer idCarrito;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Column(nullable = false, length = 100)
    private String comuna;

    @NotBlank(message = "La región es obligatoria")
    @Column(nullable = false, length = 100)
    private String region;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false, length = 30)
    private String estado;
}
