package com.microservicio.usuario.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;

    @NotBlank(message = "El email no puede estar vacio")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
    
}
