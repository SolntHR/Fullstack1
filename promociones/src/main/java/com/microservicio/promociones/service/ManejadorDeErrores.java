package com.microservicio.promociones.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservicio.promociones.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorDTO> manejarErroresDeValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        ErrorDTO errorDTO = new ErrorDTO(LocalDateTime.now(), 400, "Error de validación", errores, request.getRequestURI());
        return ResponseEntity.status(400).body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeBaseDeDatos(DataIntegrityViolationException ex, HttpServletRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(LocalDateTime.now(), 400, "Error de base de datos: ", null, request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);
    }

}
