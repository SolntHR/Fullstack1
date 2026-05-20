package com.microservicio.promociones.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PromocionesSimpleDTO {

    private String nombrePromocion;
    private String codigoPromocional;
    private BigDecimal descuento;

}