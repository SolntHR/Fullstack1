package com.compra.carrito.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    @Autowired
    private InventarioCliente inventarioCliente;
    private CarritoRepository carritoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Pago buscar(Integer id) {
        return pagoRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public Pago guardar(Pago pago) {

        pago.setEstado("APROBADO");
        pago.setFechaCreacion(LocalDateTime.now());
        Pago pagoGuardado = pagoRepository.save(pago);

        
        Carrito carrito = carritoRepository.findById(pago.getIdCarrito().longValue())
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        for(ItemCarrito item : carrito.getItems()) {

        inventarioCliente.descontarStock(
            item.getIdproducto(),
            item.getCantidad()
        );
            }

        return pagoGuardado;
    }

    public void eliminar(Integer id) {
        pagoRepository.deleteById(id.longValue());
    }


}