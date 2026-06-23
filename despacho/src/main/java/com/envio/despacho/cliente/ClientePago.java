package com.envio.despacho.cliente;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.envio.despacho.dto.CarritoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientePago {

    private final RestTemplate restTemplate;
    @Value("${carrito.service.url}")
    private String carritoServiceUrl;

    public CarritoDTO obtenerCarrito(Integer idCarrito) {
        try{    
            String url = carritoServiceUrl + "/carrito/buscar/" + idCarrito;

            return restTemplate.getForObject(
                    url,
                    CarritoDTO.class
            );
        }catch (Exception e) {
            return null;
        }
    
    }

}
