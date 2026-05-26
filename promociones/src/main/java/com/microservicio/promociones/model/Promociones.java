package com.microservicio.promociones.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Promociones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Promociones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromocion;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombrePromocion;

    @NotBlank(message = "El codigo promocional no puede estar vacio")
    @Column(name = "codigo_promocional", length = 25, nullable = false, unique = true)
    private String codigoPromocional;

    @NotNull(message = "El descuento no puede ser nulo")
    @Column(name = "descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal descuento;
    
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "Las veces de uso no pueden ser nulas")
    @Column(name = "veces_uso", nullable = false)
    private Integer vecesUso;

    @NotNull(message = "El monto minimo no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = false, message = "El monto minimo debe ser mayor a 0")  
    @Column(name = "monto_minimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMinimo;

}