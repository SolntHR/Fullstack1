package com.compra.carrito.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.compra.carrito.dto.PagoSimpleDTO;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
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
        pagoDTO.setId(pago.getId());
        pagoDTO.setMonto(pago.getMonto());
        pagoDTO.setEstado(pago.getEstado());
        pagoDTO.setFechaCreacion(pago.getFechaCreacion());
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