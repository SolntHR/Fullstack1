package com.envio.despacho.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificacionListadoDTO {

    private Integer idVerificacion;
    private String fechaEntrega;
    private String estadoEntrega;
}
