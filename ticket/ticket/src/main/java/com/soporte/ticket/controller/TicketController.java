package com.soporte.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.soporte.ticket.model.enums.EstadoTicket;
import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.service.TicketService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/soporte")
public class TicketController {

    @Autowired
    private TicketService service;

    @GetMapping("/listar")
    public List<Ticket> listarTickets(){
        return service.listarTickets();
    }



    
    @PostMapping("/agregarTicket")
    public ResponseEntity<Ticket> agregarTicket(@Valid @RequestBody Ticket ticket) {
        Ticket nuevoTicket = service.agregarTicket(ticket);
        return ResponseEntity.status(201).body(nuevoTicket);
    }
    
    @PutMapping("/actualizar/{idTicket}")
    public ResponseEntity<String> actualizarTicket(@PathVariable Integer idTicket, @RequestBody Ticket ticketActualizado) {
        Optional<Ticket> ticket = service.ticketUpdate(idTicket, ticketActualizado);
        if(ticket.isPresent()){
            return ResponseEntity.status(200).body("Ticket actualizado exitosamente");
        }
        return ResponseEntity.status(400).body("El ticket que indica no ha sido encontrado");
    }
    
    @DeleteMapping("/eliminar/{idTicket}")
    public ResponseEntity<String> eliminarTicket(@PathVariable Integer idTicket){
        Optional<Ticket> ticket = service.buscarPorId(idTicket);
        if(ticket.isPresent()){
            service.eliminarTicket(idTicket);
            return ResponseEntity.status(200).body("Ticket eliminado con exito");
        }
        return ResponseEntity.status(400).body("El ticket que indica no ha sido encontrado");
    }

    //dto
    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
