package com.envio.despacho.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificacionDetalladoDTO {

    private Integer idVerificacion;
    private Integer idEnvio;
    private String fechaEntrega;
    private String estadoEntrega;
    private String observacion;
}
