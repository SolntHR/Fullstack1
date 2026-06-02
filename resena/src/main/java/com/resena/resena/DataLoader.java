package com.resena.resena;

import com.resena.resena.model.Resena;
import com.resena.resena.repository.ResenaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initResenas(ResenaRepository repository) {
        return args -> {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (!repository.existsByIdUsuarioAndIdProducto(1, 1)) {
                Resena r1 = new Resena();
                r1.setIdUsuario(1);
                r1.setIdProducto(1);
                r1.setIdPago(1);
                r1.setEstrellas(5);
                r1.setComentario("Excelente producto, llegó en muy buen estado y funcionó sin problemas.");
                r1.setFechaCreacion(LocalDateTime.parse("23/05/2026 14:30", formatter));
                repository.save(r1);
            }

            if (!repository.existsByIdUsuarioAndIdProducto(1, 2)) {
                Resena r2 = new Resena();
                r2.setIdUsuario(1);
                r2.setIdProducto(2);
                r2.setIdPago(1);
                r2.setEstrellas(4);
                r2.setComentario("Buen mouse, cómodo y fácil de usar. La batería dura bastante.");
                r2.setFechaCreacion(LocalDateTime.parse("23/05/2026 15:10", formatter));
                repository.save(r2);
            }

            if (!repository.existsByIdUsuarioAndIdProducto(2, 3)) {
                Resena r3 = new Resena();
                r3.setIdUsuario(2);
                r3.setIdProducto(3);
                r3.setIdPago(2);
                r3.setEstrellas(5);
                r3.setComentario("Teclado excelente, muy buena respuesta de las teclas y buena construcción.");
                r3.setFechaCreacion(LocalDateTime.parse("24/05/2026 18:20", formatter));
                repository.save(r3);
            }

            if (!repository.existsByIdUsuarioAndIdProducto(2, 4)) {
                Resena r4 = new Resena();
                r4.setIdUsuario(2);
                r4.setIdProducto(4);
                r4.setIdPago(2);
                r4.setEstrellas(4);
                r4.setComentario("Muy buena calidad de imagen y tamaño ideal para estudiar y jugar.");
                r4.setFechaCreacion(LocalDateTime.parse("24/05/2026 19:00", formatter));
                repository.save(r4);
            }
        };
    }
}