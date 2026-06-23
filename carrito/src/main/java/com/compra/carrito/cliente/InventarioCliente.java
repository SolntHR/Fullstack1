package com.compra.carrito.cliente;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.compra.carrito.dto.ItemDTO;

@Service
public class InventarioCliente {

    private final RestTemplateSelector restTemplateSelector;
    private final String inventarioBaseUrl;

    public InventarioCliente(
            RestTemplateSelector restTemplateSelector,
            @Value("${services.inventario.base-url:http://inventario}") String inventarioBaseUrl) {
        this.restTemplateSelector = restTemplateSelector;
        this.inventarioBaseUrl = inventarioBaseUrl;
    }

    public ItemDTO obtenerProducto(Integer idproducto) {
        String url = inventarioBaseUrl + "/inventario/producto/productoI/" + idproducto;
        return restTemplateSelector.select(inventarioBaseUrl).getForObject(
                url,
                ItemDTO.class
        );
    }

    public void descontarStock(Integer idproducto, Integer cantidad) {
        String url = inventarioBaseUrl + "/inventario/producto/" + idproducto + "/descontar/" + cantidad;
        restTemplateSelector.select(inventarioBaseUrl).put(url, null);
    }
}
