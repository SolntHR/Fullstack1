package com.soporte.ticket.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.model.enums.EstadoTicket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer>{

    Optional<Ticket> findByIdTicket(Integer idTicket);

    List<Ticket> findByIdCliente(Long idCliente);

    List<Ticket> findByEstado(EstadoTicket estado);

}
