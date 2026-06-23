package com.compra.carrito.service;

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

    public PagoService(
            PagoRepository pagoRepository,
            InventarioCliente inventarioCliente,
            CarritoRepository carritoRepository,
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

        Carrito carrito = carritoRepository.findById(pago.getIdCarrito())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Pago pagoExistente = pagoRepository.findByIdCarrito(pago.getIdCarrito())
                .orElseGet(Pago::new);

        pagoExistente.setIdCarrito(carrito.getIdCarrito());
        pagoExistente.setMetodoPago(pago.getMetodoPago());
        pagoExistente.setMonto(carrito.getTotal());
        pagoExistente.setEstado("APROBADO");
        pagoExistente.setFechaCreacion(LocalDateTime.now());

        for (ItemCarrito item : carrito.getItems()) {
            inventarioCliente.descontarStock(
                    item.getIdProducto(),
                    item.getCantidad()
            );
        }

        return pagoRepository.save(pagoExistente);
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

    public List<PagoSimpleDTO> buscarPorRangoSimple(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
    
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

    public boolean validarCompra(Integer idPago, Integer idUsuario, Integer idProducto) {
        Pago pago = pagoRepository.findById(idPago).orElse(null);
        if (pago == null) {
            return false;
        }
        if (pago.getEstado() == null || !pago.getEstado().equalsIgnoreCase("APROBADO")) {
            return false;
        }
        Carrito carrito = carritoRepository.findById(pago.getIdCarrito())
                .orElse(null);
        if (carrito == null) {
            return false;
        }
        if (carrito.getIdUsuario() == null || !carrito.getIdUsuario().equals(idUsuario)) {
            return false;
        }
        return carrito.getItems().stream()
                .anyMatch(item -> item.getIdProducto().equals(idProducto));
    }

}
