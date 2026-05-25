package com.soporte.ticket.model;

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

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;
    
    @NotBlank(message = "La descripción no puede estar vacia")
    @Size(min = 20, max = 2000, message = "Por favor, explica el problema con más detalle (mínimo 20 caracteres)")
    @Column(nullable = false, length = 2000)     // Esto puede estar en las demás variables. Indica que ese atributo no puede ir vacio (null) y el largo del varchar es 100.
    private String descripcion;
    
    @Column(name = "fechaCreacion", nullable = false)
    private String fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTicket estado;

}
