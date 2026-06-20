package com.microservicio.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="rol")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador único del usuario")
    private Integer idRol;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre del rol debe tener entre 3 y 20 caracteres")
    @Column(name = "nombre", length = 20, nullable = false, unique = true)
    private String nombreRol;

}