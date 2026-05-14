package com.microservicio.usuario.dto.erroresDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;
    private int status;
    private String mensaje;
    private Map<String, String> errores; // Para errores de validación
    private String path;

    // Constructor para crear el objeto con todos sus datos
    public ErrorDTO(LocalDateTime timestamp, int status, String mensaje, Map<String, String> errores, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;
    }
}
