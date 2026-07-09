package com.microservicio.reportes.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Servicio de Reportes")
                .description("API REST encargada de la gestión de reportes dentro del sistema")
                .version("1.0")
                .contact(new Contact().name("Solange Hernández, Jesus Oropeza y Maximiliano Quezada")
                                    .url("https://github.com/SolntHR/Fullstack1")));
    }
}