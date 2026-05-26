package com.microservicio.promociones.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDTO {

     private LocalDateTime timestamp;
    private int status;
    private String mensaje;
    private Map<String, String> errores;
    private String path;

    public ErrorDTO(LocalDateTime timestamp, int status, String mensaje, Map<String, String> errores, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;
    }

}
