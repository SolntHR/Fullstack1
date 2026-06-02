package com.resena.resena;

import com.resena.resena.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejoDeErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarErroresValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de validación",
                errors,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresBaseDatos(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de integridad en la base de datos",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorDTO> manejarServicioPagoNoDisponible(
            ResourceAccessException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                503,
                "No se pudo conectar al microservicio de pagos",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarRuntime(
            RuntimeException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                ex.getMessage(),
                null,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErrorGeneral(
            Exception ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                500,
                "Error interno del servidor",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
