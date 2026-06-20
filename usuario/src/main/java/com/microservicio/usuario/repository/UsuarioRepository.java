package com.microservicio.usuario.repository;

import com.microservicio.usuario.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByIdUsuario(Integer idUsuario);

    List<Usuario> findByNombre(String nombre);

    List<Usuario> findByApellido(String apellido);

    Optional<Usuario> findByEmailIgnoreCase(String email);

}
