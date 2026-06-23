package com.compra.carrito.swagger;

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
                .title("API Carrito y Pagos")
                .description("Documentación de endpoints del microservicio de carrito y pagos")
                .version("1.0")
                .contact(new Contact().name("Solange Hernández, Jesus Oropeza y Maximiliano Quezada")
                                    .url("https://github.com/SolntHR/Fullstack1")));

    }
}
