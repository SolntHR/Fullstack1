package com.catalogo.inventario.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Servicio de Inventario")
                        .description("Microservicio REST encargado de administrar productos y categorías del catálogo, incluyendo operaciones CRUD y generación de respuestas DTO para distintos niveles de detalle")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Solange Hernández, Jesus Oropeza y Maximiliano Quezada")
                                .url("https://github.com/SolntHR/Fullstack1")));
    }
}
