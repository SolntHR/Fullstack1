package com.envio.despacho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.envio.despacho.cliente.ClientePago;
import com.envio.despacho.dto.CarritoDTO;
import com.envio.despacho.dto.EnvioDetalladoDTO;
import com.envio.despacho.dto.EnvioListadoDTO;
import com.envio.despacho.dto.EnvioSimpleDTO;
import com.envio.despacho.exception.Excepcion;
import com.envio.despacho.model.Envio;
import com.envio.despacho.repository.EnvioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final ClientePago clientePago;

    public List<Envio> listar() {
        return envioRepository.findAll();
    }

    public Optional<Envio> buscar(Integer idEnvio) {
        return envioRepository.findById(idEnvio);
    }

    public Envio guardar(Envio envio) {
        CarritoDTO carrito = clientePago.obtenerCarrito(envio.getIdCarrito());

        if (carrito == null) {
            throw new RuntimeException("Carrito no encontrado");
        }

        return envioRepository.save(envio);
    }

    public Envio actualizar(Integer id, Envio envio) {
        envio.setIdEnvio(id);
        return envioRepository.save(envio);
    }

    public void eliminar(Integer id) {
        envioRepository.deleteById(id);
    }

    public List<EnvioListadoDTO> listarDTO() {
        List<EnvioListadoDTO> listaDTO = new ArrayList<>();

        for (Envio envio : envioRepository.findAll()) {
            EnvioListadoDTO dto = new EnvioListadoDTO();
            dto.setIdEnvio(envio.getIdEnvio());
            dto.setDireccion(envio.getDireccion());
            dto.setEstado(envio.getEstado());
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public List<EnvioSimpleDTO> listarSimpleDTO() {
        List<EnvioSimpleDTO> listaDTO = new ArrayList<>();

        for (Envio envio : envioRepository.findAll()) {
            EnvioSimpleDTO dto = new EnvioSimpleDTO();
            dto.setIdEnvio(envio.getIdEnvio());
            dto.setEstado(envio.getEstado());
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public EnvioDetalladoDTO obtenerDetalleDTO(Integer idEnvio) {
        Envio envio = envioRepository.findById(idEnvio)
                .orElseThrow(() -> new Excepcion("Envío no encontrado con ID: " + idEnvio));

        EnvioDetalladoDTO dto = new EnvioDetalladoDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setIdCarrito(envio.getIdCarrito());
        dto.setDireccion(envio.getDireccion());
        dto.setComuna(envio.getComuna());
        dto.setRegion(envio.getRegion());
        dto.setEstado(envio.getEstado());

        return dto;
    }
}
