package com.soporte.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.model.enums.EstadoTicket;
import com.soporte.ticket.repository.TicketRepository;

import com.soporte.ticket.dto.TicketDetalleDTO;
import com.soporte.ticket.dto.TicketListadoDTO;
import com.soporte.ticket.dto.TicketSimpleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TicketService {
    @Autowired
    private TicketRepository repository;

    public List<Ticket> listar(){
        return repository.findAll();
    }

    public Optional<Ticket> buscarPorId(Integer idTicket){
        return repository.findById(idTicket);
    }

    public List<Ticket> buscarPorIdCliente(Long idCliente){
        return repository.findByIdCliente(idCliente);
    }

    public List<Ticket> buscarPorEstado(EstadoTicket estado){
        return repository.findByEstado(estado);
    }

    public Ticket agregarTicket(Ticket ticket){
        return repository.save(ticket);
    }

    public Optional<Ticket> ticketUpdate(Integer idTicket, Ticket ticketActualizado) {
        return repository.findById(idTicket).map(ticketExistente -> {
            ticketExistente.setIdCliente(ticketActualizado.getIdCliente());
            ticketExistente.setDescripcion(ticketActualizado.getDescripcion());
            ticketExistente.setFechaCreacion(ticketActualizado.getFechaCreacion());
            ticketExistente.setEstado(ticketActualizado.getEstado()); // FK
            return repository.save(ticketExistente);
        });
    }

    public boolean eliminar(Integer idTicket){
        if(repository.existsById(idTicket)){
            repository.deleteById(idTicket);
            return true;
        }
        return false;
    }

    public List<TicketListadoDTO> listarDTO(){
        List<Ticket> tickets = repository.findAll();
        List<TicketListadoDTO> listaDTO = new ArrayList<>();

        for(Ticket t : tickets)
    }

}
