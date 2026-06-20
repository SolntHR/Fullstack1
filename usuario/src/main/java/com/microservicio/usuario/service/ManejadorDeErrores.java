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
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ManejadorDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        log.warn("Error de validación en la ruta {}: {}", request.getRequestURI(), errores);

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de validación",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeNegocio(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        log.warn("Error de negocio en la ruta {}: {}", request.getRequestURI(), ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                409,
                ex.getMessage(),
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDeBaseDeDatos(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.error("Error de integridad de datos en la ruta {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                409,
                "Conflicto de integridad de datos en la base de datos",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErroresGenerales(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("Error interno en la ruta {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                500,
                ex.getMessage(),
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(errorDTO);
    }
}