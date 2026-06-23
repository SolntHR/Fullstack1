package com.envio.despacho.exception;

import java.time.LocalDateTime;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.envio.despacho.dto.ErrorDTO;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class ExceptionAll {
 
    @ExceptionHandler(Excepcion.class)
    public ResponseEntity<ErrorDTO> manejarNotFound(
            Excepcion ex) {

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                404,
                ex.getMessage(),
                null,
                null);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(
        MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(
            MethodArgumentNotValidException ex) {

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                400,
                ex.getBindingResult()
                  .getFieldError()
                  .getDefaultMessage(),
                  null
                  ,null);

        return ResponseEntity
                .badRequest()
                .body(error);
    }
}
