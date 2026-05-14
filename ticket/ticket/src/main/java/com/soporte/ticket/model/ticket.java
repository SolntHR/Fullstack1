package com.soporte.ticket.model;

import java.time.LocalDateTime;

import com.soporte.ticket.model.enums.EstadoTicket;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity                     // Indica que esta clase es una entidad que se mapeará a una tabla en la BD
@Table(name = "tickets")   // Define el nombre de la tabla en la base de datos
@Data                       // Genera automaticamente los getters, setters, toString, equals y hashCode
@NoArgsConstructor          // Genera un constructor vacio (necesario para JPA)
@AllArgsConstructor         // Genera un contructor con todos los atributos

// BASE DE DATOS LARAGON: CREATE DATABASE SOPORTE;
public class ticket {

    // idTicket
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    // id cliente lo recibe del ms gestión de usuario
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    // descripción
    @NotBlank(message = "La descripción no puede estar vacia")
    @Size(min = 20, max = 2000, message = "Por favor, explica el problema con más detalle (mínimo 20 caracteres)")
    @Column(nullable = false, length = 2000)     // Esto puede estar en las demás variables. Indica que ese atributo no puede ir vacio (null) y el largo del varchar es 100.
    private String descripcion;
    
    // fecha creación
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // estado (ABIERTO, EN PROCESO, CERRADO)
    @Enumerated(EnumType.STRING)
    private EstadoTicket estado;

}
