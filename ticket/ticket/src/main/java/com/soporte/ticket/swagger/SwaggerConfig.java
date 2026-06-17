package com.soporte.ticket.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI ticketsOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Servicio de Tickets")
                .description("API REST para gestión de tickets de soporte, para clientes, administraores y empleados")
                .version("1.0")
                .contact(new Contact().name("Solange Hernández - Jesus Oropeza - Maximiliano Quezada")
                                    .email("so.hernandezr@duocuc.cl - je.oropeza@duocuc.cl - max.quezadac@duocuc.cl")
                                    .url("https://github.com/SolntHR/Fullstack1")));

    }
}
