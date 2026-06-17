package com.envio.despacho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="verificacionDespacho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVerificacion;

    @NotNull(message = "El ID del envío es obligatorio")
    private Integer idEnvio;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private String fechaEntrega;

    @NotBlank(message = "El estado de entrega es obligatorio")
    @Column(nullable = false, length = 30)
    private String estadoEntrega;

    @Column(length = 500)
    private String observacion;

}
