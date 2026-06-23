package com.compra.carrito.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.dto.PagoSimpleDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final InventarioCliente inventarioCliente;
    private final CarritoRepository carritoRepository;
    private final CarritoService carritoService;

    public PagoService(PagoRepository pagoRepository,
                    CarritoRepository carritoRepository,
                    InventarioCliente inventarioCliente,
                    CarritoService carritoService) {
        this.pagoRepository = pagoRepository;
        this.inventarioCliente = inventarioCliente;
        this.carritoRepository = carritoRepository;
        this.carritoService = carritoService;
    }

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Pago buscar(Integer id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public Pago guardar(Pago pago) {
        if (pago.getEstado() == null || pago.getEstado().isBlank()) {
            pago.setEstado("PENDIENTE");
        }

        if (pago.getFechaCreacion() == null) {
            pago.setFechaCreacion(LocalDateTime.now());
        }

        return pagoRepository.save(pago);
    }
    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }

    public PagoSimpleDTO obtenerPagoSimpleDTO(Integer id){
        Optional<Pago> pagoOpt = pagoRepository.findById(id);

        if(pagoOpt.isEmpty()){
            return null;
        }

        Pago pago = pagoOpt.get();
        PagoSimpleDTO pagoDTO = new PagoSimpleDTO();
        pagoDTO.setIdPago(pago.getIdPago());
        pagoDTO.setMonto(pago.getMonto());
        pagoDTO.setEstado(pago.getEstado());
        pagoDTO.setFechaCreacion(pago.getFechaCreacion());
        pagoDTO.setMetodoPago(pago.getMetodoPago());
        return pagoDTO;
    }

    public List<PagoSimpleDTO> buscarPorRangoSimple(LocalDate inicio, LocalDate fin) {
        LocalDateTime fechaInicio = inicio.atStartOfDay();
        LocalDateTime fechaFin = fin.atTime(23, 59, 59);
        return pagoRepository.findByFechaCreacionBetween(fechaInicio, fechaFin)
                .stream()
                .map(pago -> {
                    PagoSimpleDTO dto = new PagoSimpleDTO();
                    dto.setIdPago(pago.getIdPago());
                    dto.setMonto(pago.getMonto());
                    dto.setEstado(pago.getEstado());
                    dto.setFechaCreacion(pago.getFechaCreacion());
                    dto.setMetodoPago(pago.getMetodoPago());
                    return dto;
                }).toList();
    }

    public Pago procesarPago(Pago pago) {
        Carrito carrito = carritoService.buscar(pago.getIdCarrito());

        for (ItemCarrito item : carrito.getItems()) {
            inventarioCliente.descontarStock(item.getIdProducto(), item.getCantidad());
        }

        pago.setEstado("APROBADO");
        pago.setFechaCreacion(LocalDateTime.now());
        
        return pagoRepository.save(pago);
    }

}
