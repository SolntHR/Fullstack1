package com.soporte.ticket;

import com.soporte.ticket.dto.ErrorDTO;
import com.soporte.ticket.excepciones.RemoteServiceException;
import com.soporte.ticket.excepciones.ResourceNotFoundException;

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
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class ManejoDeErrores {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorDTO> manejarErroresValidacion(
                MethodArgumentNotValidException ex,
                HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
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
        public ResponseEntity<ErrorDTO> manejarServicioUsuarioNoDisponible(
                ResourceAccessException ex,
                HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                503,
                "No se pudo conectar al microservicio de usuarios",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDTO);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(
                ResourceNotFoundException ex,
                HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "No se encontró el ticket",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
        }

        @ExceptionHandler(RemoteServiceException.class)
        public ResponseEntity<ErrorDTO> manejarErrorServicioRemoto(
                RemoteServiceException ex,
                HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                503,
                "El servicio de usuarios no está disponible en este momento. Intente nuevamente más tarde",
                null,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDTO);
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
