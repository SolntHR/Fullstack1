package com.microservicio.reportes.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor 

public class Reportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del reporte generado automáticamente", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idReporte;

    @NotBlank(message = "El nombre del reporte no puede estar vacio")
    @Column(name = "nombreReporte", length = 20, nullable = false, unique = true)
    private String nombreReporte;

    @NotBlank(message = "La descripcion del reporte no puede estar vacia")
    @Column(name = "descripcionReporte", length = 100, nullable = false)
    private String descripcionReporte;

    @NotNull(message = "El tipo del reporte no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoReporte", length = 20, nullable = false)
    private TipoReporte tipoReporte;

    @NotNull(message = "La fecha de inicio del reporte no puede ser nula")
    @Column(name = "fechaInicio", nullable = false)
    @Schema(description = "Fecha de inicio del reporte")
    private LocalDateTime fechaInicio; 

    @NotNull(message = "La fecha de fin del reporte no puede ser nula")
    @Column(name = "fechaFin", nullable = false)
    @Schema(description = "Fecha de fin del reporte")
    private LocalDateTime fechaFin;

}
