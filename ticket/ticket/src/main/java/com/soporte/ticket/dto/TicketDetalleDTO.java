package com.soporte.ticket.dto;

import com.soporte.ticket.model.enums.EstadoTicket;

import lombok.Data;
@Data
public class TicketDetalleDTO {
private Integer idTicket;
    private Long idCliente;
    private String descripcion;
    private String fechaCreacion;
    private EstadoTicket estado;
}
