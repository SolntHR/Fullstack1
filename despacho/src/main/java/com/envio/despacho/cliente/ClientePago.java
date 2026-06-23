package com.envio.despacho.cliente;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.envio.despacho.dto.CarritoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientePago {

    private final RestTemplateSelector restTemplateSelector;

    @Value("${services.carrito.base-url:http://carrito}")
    private String carritoBaseUrl;

    public CarritoDTO obtenerCarrito(Integer idCarrito) {
        try {
            String url = carritoBaseUrl + "/carrito/buscar/" + idCarrito;
            return restTemplateSelector.select(carritoBaseUrl).getForObject(url, CarritoDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
