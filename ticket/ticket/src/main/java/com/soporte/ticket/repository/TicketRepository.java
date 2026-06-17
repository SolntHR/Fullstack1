package com.soporte.ticket.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.model.enums.EstadoTicket;

public interface TicketRepository extends JpaRepository<Ticket,Integer>{

    Optional<Ticket> findByIdTicket(Integer idTicket);

    List<Ticket> findByIdUsuario(Integer idUsuario);

    List<Ticket> findByEstado(EstadoTicket estado);

}
