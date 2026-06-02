package com.compra.carrito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.compra.carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Integer>{

    Optional<Carrito> findByIdUsuario(Integer idUsuario);
    
}
