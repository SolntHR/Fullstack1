package com.microservicio.promociones.swagger;

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
                .title("Servicio de Promociones")
                .description("Microservicio REST encargado de la gestión integral de promociones y descuentos del sistema. Permite crear, actualizar, consultar, listar, buscar por ID, filtrar por rango de fechas y eliminar promociones, facilitando la administración de campañas comerciales y beneficios aplicados a los productos. ")
                .version("1.0")
                .contact(new Contact().name("Solange Hernández, Jesus Oropeza y Maximiliano Quezada")
                                    .url("https://github.com/SolntHR/Fullstack1")));
    }
}