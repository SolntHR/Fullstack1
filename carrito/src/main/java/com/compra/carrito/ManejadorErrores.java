package com.compra.carrito;

import com.compra.carrito.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorDTO> manejarErroresValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request){

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),400,"Error de validación",errores,request.getRequestURI());

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)

    public ResponseEntity<ErrorDTO> manejarErroresBaseDatos(
            DataIntegrityViolationException ex,
            HttpServletRequest request){

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),400,"Error en la base de datos",null,request.getRequestURI());

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)

    public ResponseEntity<ErrorDTO> manejarErroresRuntime(
            RuntimeException ex,
            HttpServletRequest request){

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),404,ex.getMessage(),null,request.getRequestURI());
                
        return ResponseEntity.status(404).body(errorDTO);
    }
}