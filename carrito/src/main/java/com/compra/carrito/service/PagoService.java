package com.compra.carrito.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private InventarioCliente inventarioCliente;
    private CarritoRepository carritoRepository;

    public PagoService(PagoRepository pagoRepository, CarritoRepository carritoRepository) {
    this.pagoRepository = pagoRepository;
    this.carritoRepository = carritoRepository;
}

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Pago buscar(Integer id) {
        return pagoRepository.findById(id)
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
        pagoRepository.deleteById(id);
    }

    public PagoSimpleDTO obtenerPagoSimpleDTO(Integer id){
        Optional<Pago> pagoOpt = pagoRepository.findById(id);

        if(pagoOpt.isEmpty()){
            return null;
        }

        Pago pago = pagoOpt.get();
        PagoSimpleDTO pagoDTO = new PagoSimpleDTO();
        pagoDTO.setId(pago.getId());
        pagoDTO.setMonto(pago.getMonto());
        pagoDTO.setEstado(pago.getEstado());
        pagoDTO.setFechaCreacion(pago.getFechaCreacion());
        pagoDTO.setMetodoPago(pago.getMetodoPago());
        return pagoDTO;
    }

    public List<PagoSimpleDTO> buscarPorRangoSimple(String inicio, String fin) {
    LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
    LocalDateTime fechaFin = LocalDateTime.parse(fin);

    return pagoRepository.findByFechaCreacionBetween(fechaInicio, fechaFin)
            .stream()
            .map(pago -> {
                PagoSimpleDTO dto = new PagoSimpleDTO();
                dto.setId(pago.getId());
                dto.setMonto(pago.getMonto());
                dto.setEstado(pago.getEstado());
                dto.setFechaCreacion(pago.getFechaCreacion());
                dto.setMetodoPago(pago.getMetodoPago());
                return dto;
            }).toList();

    }
}