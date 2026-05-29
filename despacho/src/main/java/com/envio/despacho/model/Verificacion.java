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

    @NotNull
    private Integer idEnvio;

    @NotBlank
    private String fechaEntrega;

    @NotBlank
    private String estadoEntrega;

    private String observacion;

}
