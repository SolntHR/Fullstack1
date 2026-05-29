package com.envio.despacho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.envio.despacho.cliente.ClientePago;
import com.envio.despacho.dto.CarritoDTO;
import com.envio.despacho.model.Envio;
import com.envio.despacho.repository.EnvioRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final ClientePago clientePago;

    public List<Envio> listar(){
        return envioRepository.findAll();
    }

    public Optional<Envio> buscar(Integer idEnvio) {
        return envioRepository.findById(idEnvio);
    }

    public Envio guardar(Envio envio) {
        CarritoDTO carrito = clientePago.obtenerCarrito(envio.getIdCarrito());

        if(carrito == null) {
            throw new RuntimeException(
                    "Carrito no encontrado");
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
}
