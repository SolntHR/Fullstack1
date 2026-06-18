package com.soporte.ticket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soporte.ticket.model.enums.EstadoTicket;
import com.soporte.ticket.dto.TicketDetalleDTO;
import com.soporte.ticket.dto.TicketListadoDTO;
import com.soporte.ticket.dto.TicketSimpleDTO;
import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.service.TicketService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/soporte")
@Tag(name = "Ticket Controller", description = "Endpoints para la gestión de tickets de soporte")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los tickets", description = "Obtiene la lista completa de tickets registrados",tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tickets obtenida correctamente")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Ticket>> listarTickets() {
        return ResponseEntity.ok(service.listarTickets());
    }
    
    @Operation(summary = "Buscar ticket por ID", description = "Obtiene un ticket específico a partir de su identificador",tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el ticket")
    })
    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<Ticket> buscarPorIdTicket(@PathVariable Integer idTicket) {
        return ResponseEntity.ok(service.buscarPorIdTicket(idTicket));
    }


    @Operation(summary = "Buscar tickets por usuario", description = "Obtiene todos los tickets asociados a un usuario",tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets del usuario obtenidos correctamente")
    })
    @GetMapping("/cliente/{idUsuario}")
    public ResponseEntity<List<Ticket>> buscarPorIdUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.buscarPorIdUsuario(idUsuario));
    }

    @Operation(summary = "Buscar tickets por estado", description = "Obtiene los tickets filtrados según su estado",tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets filtrados correctamente")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ticket>> buscarPorEstado(@PathVariable EstadoTicket estado) {
        return ResponseEntity.ok(service.buscarPorEstado(estado));
    }

    @Operation(summary = "Agregar un ticket", description = "Crea un nuevo ticket de soporte",tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario asociado"),
            @ApiResponse(responseCode = "503", description = "Servicio de usuarios no disponible")
    })
    @PostMapping("/agregarTicket")
    public ResponseEntity<Ticket> agregarTicket(@Valid @RequestBody Ticket ticket) {
        Ticket nuevoTicket = service.agregarTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTicket);
    }
    
    @Operation(summary = "Actualizar ticket", description = "Actualiza los datos de un ticket existente",tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "No se encontró el ticket"),
            @ApiResponse(responseCode = "503", description = "Servicio de usuarios no disponible")
    })
    @PutMapping("/actualizar/{idTicket}")
    public ResponseEntity<Map<String, Object>> actualizarTicket(@PathVariable Integer idTicket,
                                                            @Valid @RequestBody Ticket ticketActualizado) {
    Ticket ticket = service.ticketUpdate(idTicket, ticketActualizado);

    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Ticket actualizado exitosamente");
    response.put("ticket", ticket);

    return ResponseEntity.ok(response);
    }   
    
    @Operation(summary = "Eliminar ticket", description = "Elimina un ticket existente a partir de su ID",tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el ticket")
    })
    @DeleteMapping("/eliminar/{idTicket}")
    public ResponseEntity<String> eliminarTicket(@PathVariable Integer idTicket) {
        service.eliminarTicket(idTicket);
        return ResponseEntity.ok("Ticket eliminado con éxito");
    }

    @Operation(summary = "Obtener listado DTO", description = "Obtiene una vista resumida de tickets con información relevante",tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente")
    })
    @GetMapping("/listadoDTO")
    public ResponseEntity<List<TicketListadoDTO>> obtenerListado() {
        return ResponseEntity.ok(service.listarDTO());
    }
    
    @Operation(summary = "Obtener listado simple DTO", description = "Obtiene una vista simple de tickets",tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado simple obtenido correctamente")
    })
    @GetMapping("/simpleDTO")
    public ResponseEntity<List<TicketSimpleDTO>> obtenerSimple() {
        return ResponseEntity.ok(service.listarSimpleDTO());
    }

    @Operation(summary = "Obtener detalle DTO", description = "Obtiene el detalle completo de un ticket en formato DTO",tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle del ticket obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el ticket")
    })
    @GetMapping("/{idTicket}/detalleDTO")
    public ResponseEntity<TicketDetalleDTO> obtenerDetalle(@PathVariable Integer idTicket) {
        return ResponseEntity.ok(service.obtenerDetalleDTO(idTicket));
    }
}
