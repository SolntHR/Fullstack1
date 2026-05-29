package com.envio.despacho.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDetalladoDTO {

    private Integer idEnvio;
    private Integer idCarrito;
    private String direccion;
    private String comuna;
    private String region;
    private String estado;
}
