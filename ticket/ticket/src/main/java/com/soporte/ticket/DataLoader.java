package com.soporte.ticket;

import com.soporte.ticket.repository.TicketRepository;
import com.soporte.ticket.model.Ticket;
import com.soporte.ticket.model.enums.EstadoTicket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(TicketRepository repository){
        return args -> {
            if (repository.count() == 0) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                Ticket t1 = new Ticket();
                t1.setIdUsuario(1);
                t1.setDescripcion("Mis audifonos venian con la caja rota y el producto llegó dañado.");
                t1.setFechaCreacion(LocalDateTime.parse("05/01/2026 10:30", formatter));
                t1.setEstado(EstadoTicket.CERRADO);

                Ticket t2 = new Ticket();
                t2.setIdUsuario(2);
                t2.setDescripcion("El teclado dejó de funcionar después de dos días de uso normal.");
                t2.setFechaCreacion(LocalDateTime.parse("18/02/2026 15:45", formatter));
                t2.setEstado(EstadoTicket.ABIERTO);

                repository.save(t1);
                repository.save(t2);

            }
        };
    }
}
