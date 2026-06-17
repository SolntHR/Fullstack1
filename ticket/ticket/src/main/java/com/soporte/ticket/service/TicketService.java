package com.soporte.ticket.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.model.enums.EstadoTicket;
import com.soporte.ticket.repository.TicketRepository;


import com.soporte.ticket.dto.TicketDetalleDTO;
import com.soporte.ticket.dto.TicketListadoDTO;
import com.soporte.ticket.dto.TicketSimpleDTO;
import com.soporte.ticket.excepciones.RemoteServiceException;
import com.soporte.ticket.excepciones.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;


@Service
public class TicketService {
    
    private final TicketRepository repository;
    private final RestTemplate restTemplate;

    @Value("${usuario.service.url}")
    private String usuarioServiceUrl;

    public TicketService(TicketRepository repository, RestTemplate restTemplate){
        this.repository = repository;
        this.restTemplate = restTemplate;
        }

    public List<Ticket> listarTickets(){
        return repository.findAll();
    }

    public Ticket buscarPorIdTicket(Integer idTicket) {
        return repository.findByIdTicket(idTicket)
                .orElseThrow(() -> new ResourceNotFoundException("No hay tickets con ID: " + idTicket));
    }

    public List<Ticket> buscarPorIdUsuario(Integer idUsuario){
        return repository.findByIdUsuario(idUsuario);
    }

    public List<Ticket> buscarPorEstado(EstadoTicket estado){
        return repository.findByEstado(estado);
    }

    public Ticket agregarTicket(Ticket ticket){
        validarUsuarioExiste(ticket.getIdUsuario());
        return repository.save(ticket);
    }


    public Ticket ticketUpdate(Integer idTicket, Ticket ticketActualizado) {
        Ticket ticketExistente = repository.findByIdTicket(idTicket)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el ticket"));

        validarUsuarioExiste(ticketActualizado.getIdUsuario());

        ticketExistente.setIdUsuario(ticketActualizado.getIdUsuario());
        ticketExistente.setDescripcion(ticketActualizado.getDescripcion());
        ticketExistente.setEstado(ticketActualizado.getEstado());

        return repository.save(ticketExistente);
    }


    public void eliminarTicket(Integer idTicket) {
        if (!repository.existsById(idTicket)) {
            throw new ResourceNotFoundException("No se encontró el ticket");
        }
        repository.deleteById(idTicket);
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

    private void validarUsuarioExiste(Integer idUsuario) {
        String url = usuarioServiceUrl + "/usuarios/existe/" + idUsuario;

        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
            Boolean existeUsuario = response.getBody();

            if (existeUsuario == null || !existeUsuario) {
                throw new ResourceNotFoundException("No se encontró el usuario asociado al ticket");
            }
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (RestClientException ex) {
            throw new RemoteServiceException("El servicio de usuarios no está disponible en este momento");
        }
    }
}
