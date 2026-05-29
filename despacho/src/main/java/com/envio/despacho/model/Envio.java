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

    @NotNull
    private Integer idCarrito;

    @NotBlank
    private String direccion;

    @NotBlank
    private String comuna;

    @NotBlank
    private String region;

    @NotBlank
    private String estado;
}
