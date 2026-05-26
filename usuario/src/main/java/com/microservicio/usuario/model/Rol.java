package com.microservicio.usuario.model;

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
    private Integer idRol;

    @NotBlank(message = "El nombre del rol no puede estar vacio")
    @Column(name = "nombre", length = 20, nullable = false, unique = true)
    private String nombreRol;

}