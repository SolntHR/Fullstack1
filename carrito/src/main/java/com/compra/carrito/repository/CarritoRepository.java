package com.compra.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.compra.carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long>{
}
