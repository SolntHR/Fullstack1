package com.compra.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.compra.carrito.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{

}
