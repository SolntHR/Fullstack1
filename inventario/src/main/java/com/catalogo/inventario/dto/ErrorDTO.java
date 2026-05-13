package com.catalogo.inventario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timesTamp;        //fecha y hora del error
    private int status;                     //Codigo del estado HTTP
    private String mensaje;                 //Mensaje general del error 
    private Map<String, String> errores;    //Detalle del error por campos
    private String path;                    //Ruta de donde ocurrio el error

    public ErrorDTO(LocalDateTime timesTamp, int status, String mensaje, Map<String, String> errores, String path){
        this.timesTamp = timesTamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;
    }


}
