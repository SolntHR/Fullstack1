package com.soporte.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

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

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    public List<Ticket> listarTickets(){
        return repository.findAll();
    }

    public Optional<Ticket> buscarPorIdTicket(Integer idTicket){
        return repository.findById(idTicket);
    }

    public List<Ticket> buscarPorIdUsuario(Integer idUsuario){
        return repository.findByIdUsuario(idUsuario);
    }

    public List<Ticket> buscarPorEstado(EstadoTicket estado){
        return repository.findByEstado(estado);
    }

    public Ticket agregarTicket(Ticket ticket){
        String url = usuarioServiceUrl + "/usuarios/existe/" + ticket.getIdUsuario();

        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        Boolean existeUsuario = response.getBody();

        if (existeUsuario == null || !existeUsuario) {
            throw new RuntimeException("El usuario con id " + ticket.getIdUsuario() + " no existe");
        }

        return repository.save(ticket);
    }


    public Optional<Ticket> ticketUpdate(Integer idTicket, Ticket ticketActualizado) {
        return repository.findById(idTicket).map(ticketExistente -> {
            ticketExistente.setIdUsuario(ticketActualizado.getIdUsuario());
            ticketExistente.setDescripcion(ticketActualizado.getDescripcion());
            ticketExistente.setEstado(ticketActualizado.getEstado());
            return repository.save(ticketExistente);
        });
    }


    public boolean eliminarTicket(Integer idTicket){
        if(repository.existsById(idTicket)){
            repository.deleteById(idTicket);
            return true;
        }
        return false;
    }

    public List<TicketListadoDTO> listarDTO(){
        List<Ticket> tickets = repository.findAll();
        List<TicketListadoDTO> listaDTO = new ArrayList<>();

        for(Ticket t : tickets){
            TicketListadoDTO dto = new TicketListadoDTO();
            dto.setIdTicket(t.getIdTicket());
            dto.setIdUsuario(t.getIdUsuario());
            dto.setDescripcion(t.getDescripcion());
            dto.setFechaCreacion(t.getFechaCreacion());
            dto.setEstado(t.getEstado());

            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public List<TicketSimpleDTO> listarSimpleDTO() {
        List<Ticket> tickets = repository.findAll();
        List<TicketSimpleDTO> listaDTO = new ArrayList<>();

        for (Ticket t : tickets) {
            TicketSimpleDTO dto = new TicketSimpleDTO();
            dto.setIdTicket(t.getIdTicket());
            dto.setDescripcion(t.getDescripcion());
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public TicketDetalleDTO obtenerDetalleDTO(Integer idTicket){
        Ticket t = repository.findByIdTicket(idTicket).orElse(null);
        TicketDetalleDTO dto = new TicketDetalleDTO();
        dto.setIdTicket(t.getIdTicket());
        dto.setIdUsuario(t.getIdUsuario());
        dto.setDescripcion(t.getDescripcion());
        dto.setFechaCreacion(t.getFechaCreacion());
        dto.setEstado(t.getEstado());
        return dto;
    }
}
