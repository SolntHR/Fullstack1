package com.envio.despacho.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioListadoDTO {

    private Integer idEnvio;
    private String direccion;
    private String estado;
    
}
