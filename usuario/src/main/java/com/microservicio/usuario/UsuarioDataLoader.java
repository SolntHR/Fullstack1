package com.microservicio.usuario;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.RolRepository;
import com.microservicio.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@Slf4j
public class UsuarioDataLoader {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository userRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            log.info("Iniciando carga de datos iniciales");

            Rol adminRol = crearRolSiNoExiste(rolRepo, "ADMIN");
            Rol userRol = crearRolSiNoExiste(rolRepo, "USER");

            if (userRepo.findByEmailIgnoreCase("admin@pizza.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setApellido("Sistema");
                admin.setEmail("admin@pizza.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setRol(adminRol);
                userRepo.save(admin);
                log.info("Usuario inicial creado: {}", admin.getEmail());
            } else {
                log.info("El usuario inicial {} ya existe", "admin@pizza.com");
            }

            if (userRepo.findByEmailIgnoreCase("pepe@gmail.com").isEmpty()) {
                Usuario pepe = new Usuario();
                pepe.setNombre("Pepe");
                pepe.setApellido("Pizzero");
                pepe.setEmail("pepe@gmail.com");
                pepe.setPassword(passwordEncoder.encode("pepe123"));
                pepe.setRol(userRol);
                userRepo.save(pepe);
                log.info("Usuario inicial creado: {}", pepe.getEmail());
            } else {
                log.info("El usuario inicial {} ya existe", "pepe@gmail.com");
            }

            log.info("Carga de datos iniciales finalizada");
        };
    }

    private Rol crearRolSiNoExiste(RolRepository repo, String nombre) {
        Optional<Rol> rolOpt = repo.findByNombreRol(nombre);

        if (rolOpt.isPresent()) {
            log.info("El rol {} ya existe", nombre);
            return rolOpt.get();
        } else {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombreRol(nombre);
            Rol rolGuardado = repo.save(nuevoRol);
            log.info("Rol inicial creado: {}", nombre);
            return rolGuardado;
        }
    }
}