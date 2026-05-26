package com.compra.carrito.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timesTamp;      
    private int status;                     
    private String mensaje;                 
    private Map<String, String> errores;    
    private String path;                   

    public ErrorDTO(LocalDateTime timesTamp, int status, String mensaje, Map<String, String> errores, String path){
        this.timesTamp = timesTamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;
    }
}
