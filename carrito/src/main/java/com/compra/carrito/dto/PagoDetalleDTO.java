package com.compra.carrito.dto;

import lombok.Data;

@Data
public class PagoDetalleDTO {
    
    private Integer id;
    private Integer idCarrito;
    private String metodoPago;
    private Integer monto;
    private String estado;

}
