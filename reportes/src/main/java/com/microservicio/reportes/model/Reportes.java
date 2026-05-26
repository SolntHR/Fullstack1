package com.microservicio.reportes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor 

public class Reportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReporte;

    @NotBlank(message = "El nombre del reporte no puede estar vacio")
    @Column(name = "nombreReporte", length = 20, nullable = false, unique = true)
    private String nombreReporte;

    @NotBlank(message = "La descripcion del reporte no puede estar vacia")
    @Column(name = "descripcionReporte", length = 100, nullable = false)
    private String descripcionReporte;

    @NotBlank(message = "El tipo del reporte no puede estar vacio")
    @Column(name = "tipoReporte", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoReporte tipoReporte;

    @NotNull(message = "La fecha de inicio del reporte no puede ser nula")
    @Column(name = "fechaInicio", nullable = false)
    private String fechaInicio; 

    @NotNull(message = "La fecha de fin del reporte no puede ser nula")
    @Column(name = "fechaFin", nullable = false)
    private String fechaFin;

}
