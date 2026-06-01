package com.soporte.ticket.dto;

import java.time.LocalDateTime;

import com.soporte.ticket.model.enums.EstadoTicket;

import lombok.Data;

@Data
public class TicketListadoDTO {
    private Integer idTicket;
    private Integer idUsuario;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private EstadoTicket estado;
}
