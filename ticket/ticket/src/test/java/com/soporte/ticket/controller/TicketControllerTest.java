package com.soporte.ticket.controller;

import com.soporte.ticket.model.enums.EstadoTicket;
import com.soporte.ticket.service.TicketService;
import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.dto.TicketDetalleDTO;
import com.soporte.ticket.dto.TicketListadoDTO;
import com.soporte.ticket.dto.TicketSimpleDTO;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @MockitoBean
    private TicketService service;

    @Test
    @DisplayName("Debe listar todos los tickets")
    void listarTickets() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setIdUsuario(10);
        ticket.setDescripcion("Problema con acceso");
        ticket.setEstado(EstadoTicket.EN_PROCESO);

        when(service.listarTickets()).thenReturn(List.of(ticket));

        mockMvc.perform(get("/soporte/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(1))
                .andExpect(jsonPath("$[0].idUsuario").value(10))
                .andExpect(jsonPath("$[0].descripcion").value("Problema con acceso"))
                .andExpect(jsonPath("$[0].estado").value("EN_PROCESO"));
    }

    @Test
    @DisplayName("Debe buscar ticket por ID")
    void buscarPorIdTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setIdUsuario(20);
        ticket.setDescripcion("Error en plataforma");
        ticket.setEstado(EstadoTicket.CERRADO);

        when(service.buscarPorIdTicket(1)).thenReturn(ticket);

        mockMvc.perform(get("/soporte/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTicket").value(1))
                .andExpect(jsonPath("$.idUsuario").value(20))
                .andExpect(jsonPath("$.descripcion").value("Error en plataforma"))
                .andExpect(jsonPath("$.estado").value("CERRADO"));
    }

    @Test
    @DisplayName("Debe buscar tickets por usuario")
    void buscarPorIdUsuario() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(2);
        ticket.setIdUsuario(30);
        ticket.setDescripcion("Consulta de soporte");
        ticket.setEstado(EstadoTicket.ABIERTO);

        when(service.buscarPorIdUsuario(30)).thenReturn(List.of(ticket));

        mockMvc.perform(get("/soporte/cliente/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(2))
                .andExpect(jsonPath("$[0].idUsuario").value(30))
                .andExpect(jsonPath("$[0].descripcion").value("Consulta de soporte"))
                .andExpect(jsonPath("$[0].estado").value("ABIERTO"));
    }

    @Test
    @DisplayName("Debe buscar tickets por estado")
    void buscarPorEstado() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(3);
        ticket.setIdUsuario(40);
        ticket.setDescripcion("Problema técnico");
        ticket.setEstado(EstadoTicket.EN_PROCESO);

        when(service.buscarPorEstado(EstadoTicket.EN_PROCESO)).thenReturn(List.of(ticket));

        mockMvc.perform(get("/soporte/estado/EN_PROCESO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(3))
                .andExpect(jsonPath("$[0].estado").value("EN_PROCESO"));
    }

    @Test
    @DisplayName("Debe agregar un ticket")
    void agregarTicket() throws Exception {
        Ticket ticketEntrada = new Ticket();
        ticketEntrada.setIdUsuario(50);
        ticketEntrada.setDescripcion("El sistema no permite iniciar sesión correctamente");
        ticketEntrada.setEstado(EstadoTicket.ABIERTO);

        Ticket ticketGuardado = new Ticket();
        ticketGuardado.setIdTicket(4);
        ticketGuardado.setIdUsuario(50);
        ticketGuardado.setDescripcion("El sistema no permite iniciar sesión correctamente");
        ticketGuardado.setEstado(EstadoTicket.ABIERTO);

        when(service.agregarTicket(any(Ticket.class))).thenReturn(ticketGuardado);

        mockMvc.perform(post("/soporte/agregarTicket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTicket").value(4))
                .andExpect(jsonPath("$.idUsuario").value(50))
                .andExpect(jsonPath("$.descripcion").value("El sistema no permite iniciar sesión correctamente"))
                .andExpect(jsonPath("$.estado").value("ABIERTO"));
    }

    @Test
    @DisplayName("Debe actualizar un ticket")
    void actualizarTicket() throws Exception {
        Ticket ticketActualizado = new Ticket();
        ticketActualizado.setIdUsuario(60);
        ticketActualizado.setDescripcion("El ticket fue actualizado con una descripción válida");
        ticketActualizado.setEstado(EstadoTicket.CERRADO);

        Ticket ticketRespuesta = new Ticket();
        ticketRespuesta.setIdTicket(5);
        ticketRespuesta.setIdUsuario(60);
        ticketRespuesta.setDescripcion("El ticket fue actualizado con una descripción válida");
        ticketRespuesta.setEstado(EstadoTicket.CERRADO);

        when(service.ticketUpdate(eq(5), any(Ticket.class))).thenReturn(ticketRespuesta);

        mockMvc.perform(put("/soporte/actualizar/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Ticket actualizado exitosamente"))
                .andExpect(jsonPath("$.ticket.idTicket").value(5))
                .andExpect(jsonPath("$.ticket.idUsuario").value(60))
                .andExpect(jsonPath("$.ticket.descripcion").value("El ticket fue actualizado con una descripción válida"))
                .andExpect(jsonPath("$.ticket.estado").value("CERRADO"));
    }

    @Test
    @DisplayName("Debe eliminar un ticket")
    void eliminarTicket() throws Exception {
        doNothing().when(service).eliminarTicket(6);

        mockMvc.perform(delete("/soporte/eliminar/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Ticket eliminado con éxito"));
    }

    @Test
    @DisplayName("Debe obtener listado DTO")
    void obtenerListado() throws Exception {
        TicketListadoDTO dto = new TicketListadoDTO();
        dto.setIdTicket(7);
        dto.setIdUsuario(70);
        dto.setDescripcion("Listado DTO");
        dto.setFechaCreacion(LocalDateTime.of(2026, 6, 17, 12, 0));
        dto.setEstado(EstadoTicket.ABIERTO);

        when(service.listarDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/soporte/listadoDTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(7))
                .andExpect(jsonPath("$[0].idUsuario").value(70))
                .andExpect(jsonPath("$[0].descripcion").value("Listado DTO"))
                .andExpect(jsonPath("$[0].estado").value("ABIERTO"));
    }

    @Test
    @DisplayName("Debe obtener listado simple DTO")
    void obtenerSimple() throws Exception {
        TicketSimpleDTO dto = new TicketSimpleDTO();
        dto.setIdTicket(8);
        dto.setDescripcion("Simple DTO");

        when(service.listarSimpleDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/soporte/simpleDTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(8))
                .andExpect(jsonPath("$[0].descripcion").value("Simple DTO"));
    }

    @Test
    @DisplayName("Debe obtener detalle DTO")
    void obtenerDetalle() throws Exception {
        TicketDetalleDTO dto = new TicketDetalleDTO();
        dto.setIdTicket(9);
        dto.setIdUsuario(90);
        dto.setDescripcion("Detalle DTO");
        dto.setFechaCreacion(LocalDateTime.of(2026, 6, 17, 13, 0));
        dto.setEstado(EstadoTicket.EN_PROCESO);

        when(service.obtenerDetalleDTO(9)).thenReturn(dto);

        mockMvc.perform(get("/soporte/9/detalleDTO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTicket").value(9))
                .andExpect(jsonPath("$.idUsuario").value(90))
                .andExpect(jsonPath("$.descripcion").value("Detalle DTO"))
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }
}