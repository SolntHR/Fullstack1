package com.compra.carrito;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio Carrito API",
                version = "1.0.0",
                description = "Documentacion OpenAPI para la gestion de carritos y pagos.",
                contact = @Contact(name = "Equipo Fullstack"),
                license = @License(name = "Uso academico")))
public class OpenApiConfig {
}
