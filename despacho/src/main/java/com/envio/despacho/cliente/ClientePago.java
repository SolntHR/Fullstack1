package com.envio.despacho.cliente;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.envio.despacho.dto.CarritoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientePago {

    private final RestTemplate restTemplate;

    public CarritoDTO obtenerCarrito(Integer idCarrito) {
        try{    
            String url =
                    "http://localhost:8084/carrito/carrito/buscar/"
                    + idCarrito;

            return restTemplate.getForObject(
                    url,
                    CarritoDTO.class
            );
        }catch (Exception e) {
            return null;
        }
    
    }

}
