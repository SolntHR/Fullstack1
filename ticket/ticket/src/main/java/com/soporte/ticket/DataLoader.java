package com.soporte.ticket;

import com.soporte.ticket.repository.TicketRepository;
import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.model.enums.EstadoTicket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(TicketRepository repository){
        return args -> {
            if(repository.count() == 0){
                Ticket t = new Ticket();
                t.setIdCliente(1L);
                t.setDescripcion("Mis audifonos venian con la caja rota");
                t.setFechaCreacion("05/01/2024");
                t.setEstado(EstadoTicket.CERRADO);
                repository.save(t);
            }
        };
    }
}
