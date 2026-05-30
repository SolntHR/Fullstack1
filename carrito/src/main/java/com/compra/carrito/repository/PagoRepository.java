package com.compra.carrito.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.compra.carrito.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer>{

    List<Pago> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

}
