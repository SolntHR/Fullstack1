package com.compra.carrito.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PagoSimpleDTO {
    private Integer idPago;
    private Integer monto;
    private String estado;
    private LocalDateTime fechaCreacion;
    private String metodoPago;
}