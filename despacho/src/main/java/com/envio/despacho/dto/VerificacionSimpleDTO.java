package com.envio.despacho.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificacionSimpleDTO {

    private Integer idVerificacion;
    private String estadoEntrega;
}
