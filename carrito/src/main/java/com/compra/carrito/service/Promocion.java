package com.compra.carrito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.compra.carrito.dto.CuponDTO;

@Service
public class Promocion {

    @Autowired
    private RestTemplate restTemplate;

    public CuponDTO obtenerCupon(String codigoPromocional){
        String url = "http://localhost:8088/promociones/buscar-codigo/" + codigoPromocional;

        return restTemplate.getForObject(url, CuponDTO.class);
    }
}
