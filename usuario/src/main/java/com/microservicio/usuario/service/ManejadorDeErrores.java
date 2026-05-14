package com.microservicio.usuario.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservicio.usuario.dto.erroresDTO.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class) // Maneja errores de validación
    public ResponseEntity<ErrorDTO> manejarErroresDeValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) { // Recibe la excepción y la solicitud para obtener el path
        Map<String, String> errores = new HashMap<>(); // Mapa para almacenar los errores de validación (campo -> mensaje)

        ex.getBindingResult().getFieldErrors().forEach(error -> { // Itera sobre los errores de validación
            errores.put(error.getField(), error.getDefaultMessage()); // Agrega el campo y su mensaje de error al mapa
        }); 

        // Crea un objeto ErrorDTO con la información del error
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(), // Fecha y hora actual del error
            400, // Código de estado HTTP para error de validación
            "Error de validación", // Mensaje general del error
            errores, // Mapa de errores de validación
            request.getRequestURI() // url de la solicitud que causó el error
        );

        // Devuelve una respuesta con el ErrorDTO y el código de estado 400 (Bad Request)
        return ResponseEntity.badRequest().body(errorDTO);

    }

    // Manejo de errores de base datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeBaseDeDatos(
        DataIntegrityViolationException ex,
        HttpServletRequest request) {
            
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            400, 
            "EL email ya existe en la base de datos",
            null, 
            request.getRequestURI() // URL de la solicitud que causó el error
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }

}