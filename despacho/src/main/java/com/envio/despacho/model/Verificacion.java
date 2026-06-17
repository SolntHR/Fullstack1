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
    @Positive(message = "El ID del envío debe ser mayor a 0")
    private Integer idEnvio;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private String fechaEntrega;

    @NotBlank(message = "El estado de entrega es obligatorio")
    @Pattern(regexp = "PENDIENTE|ENTREGADO|RECHAZADO|DEVUELTO",
            message = "Estado de entrega no válido")
    @Column(nullable = false, length = 30)
    private String estadoEntrega;

    @Size(max = 500, message = "La observación no puede superar los 500 caracteres")
    @Column(length = 500)
    private String observacion;

}
