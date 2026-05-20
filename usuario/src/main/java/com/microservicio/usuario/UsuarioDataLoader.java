package com.microservicio.usuario;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.RolRepository;
import com.microservicio.usuario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class UsuarioDataLoader {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository userRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Crear Roles (si no existen)
            Rol adminRol = crearRolSiNoExiste(rolRepo, "ADMIN");
            Rol userRol = crearRolSiNoExiste(rolRepo, "USER");

            // 2. Crear Usuarios de prueba
            if (userRepo.findByEmailIgnoreCase("admin@pizza.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setApellido("Sistema");
                admin.setEmail("admin@pizza.com");
                admin.setPassword(passwordEncoder.encode("123456")); // Idealmente encriptada después
                admin.setRol(adminRol);
                userRepo.save(admin);
                System.out.println("Cargado usuario Admin...");
            }

            if (userRepo.findByEmailIgnoreCase("pepe@gmail.com").isEmpty()) {
                Usuario pepe = new Usuario();
                pepe.setNombre("Pepe");
                pepe.setApellido("Pizzero");
                pepe.setEmail("pepe@gmail.com");
                pepe.setPassword(passwordEncoder.encode("pepe123"));
                pepe.setRol(userRol);
                userRepo.save(pepe);
                System.out.println("Cargado usuario Pepe...");
            }
        };
    }

    // Método de apoyo para evitar duplicar roles cada vez que reinicias
    private Rol crearRolSiNoExiste(RolRepository repo, String nombre) {
        Optional<Rol> rolOpt = repo.findByNombreRol(nombre);
        if (rolOpt.isPresent()) {
            return rolOpt.get();
        } else {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombreRol(nombre);
            System.out.println("Creado rol: " + nombre);
            return repo.save(nuevoRol);
        }
    }
}