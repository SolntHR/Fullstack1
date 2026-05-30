package com.microservicio.reportes.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Pago {
    private Integer id;
    private String metodoPago;
    private Integer monto;
    private String estado;
    private LocalDateTime fechaCreacion;
}
