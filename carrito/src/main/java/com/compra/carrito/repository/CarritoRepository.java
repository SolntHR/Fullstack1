package com.compra.carrito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.compra.carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Integer>{

    List<Carrito> findByIdUsuario(Integer idUsuario);
    
}
