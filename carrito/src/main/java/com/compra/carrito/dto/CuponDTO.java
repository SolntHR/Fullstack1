package com.compra.carrito.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class CuponDTO {

    private String codigoPromocional;
    private BigDecimal descuento;
    private Integer vecesUso;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoMinimo;

}
