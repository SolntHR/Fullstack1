package com.microservicio.usuario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Seguridad {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Desactivar seguridad automaticas
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para facilitar pruebas en postman
            .authorizeHttpRequests(auth -> auth // configura permisos para las solicitudes HTTP
                .anyRequest().permitAll() // Permite acceso libre a todos los endpoints
            );
        return http.build();
    }

}
