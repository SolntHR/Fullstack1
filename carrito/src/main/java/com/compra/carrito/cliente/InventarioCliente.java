package com.compra.carrito.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.compra.carrito.dto.ItemDTO;

@Service

public class InventarioCliente {

    @Autowired
    private RestTemplate restTemplate;

    public ItemDTO obtenerProducto(Integer idproducto) {

        String url =
            "http://localhost:8083/inventario/producto/productoI/" + idproducto;

        return restTemplate.getForObject(
                url,
                ItemDTO.class
        );
    }

    public void descontarStock(Integer idproducto, Integer cantidad) {
    String url = "http://localhost:8083/inventario/producto/" + idproducto + "/descontar/" + cantidad;
    restTemplate.put(url, null);
}
}
