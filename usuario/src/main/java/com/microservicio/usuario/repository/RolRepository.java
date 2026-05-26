package com.microservicio.usuario.repository;

import com.microservicio.usuario.model.Rol;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    Optional<Rol> findByIdRol(Integer idRol);

    Optional<Rol> findByNombreRol(String nombreRol);

}
