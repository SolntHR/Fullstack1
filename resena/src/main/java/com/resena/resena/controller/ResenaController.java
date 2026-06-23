package com.resena.resena.controller;

import com.resena.resena.model.Resena;
import com.resena.resena.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.resena.resena.dto.ResenaSimpleDTO;
import com.resena.resena.dto.ResenaListadoDTO;
import com.resena.resena.dto.ResenaDetalleDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resenas")
public class ResenaController {

    private final ResenaService service;

    public ResenaController(ResenaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos las reseñas", description = "Obtiene la lista completa de reseñas registradas",tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida correctamente")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Resena>> listarResenas() {
        return ResponseEntity.ok(service.listarResenas());
    }

    
    @GetMapping("/resena/{idResena}")
    public Optional<Resena> buscarPorIdResena(@PathVariable Integer idResena) {
        return service.buscarPorIdResena(idResena);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Resena> buscarPorIdUsuario(@PathVariable Integer idUsuario) {
        return service.buscarPorIdUsuario(idUsuario);
    }

    @GetMapping("/producto/{idProducto}")
    public List<Resena> buscarPorIdProducto(@PathVariable Integer idProducto) {
        return service.buscarPorIdProducto(idProducto);
    }

    @PostMapping("/agregarResena")
    public ResponseEntity<Resena> agregarResena(@Valid @RequestBody Resena resena) {
        Resena nuevaResena = service.agregarResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
    }

    @PutMapping("/actualizar/{idResena}")
    public ResponseEntity<String> actualizarResena(@PathVariable Integer idResena, @RequestBody Resena resenaActualizada) {
        Optional<Resena> resena = service.resenaUpdate(idResena, resenaActualizada);
        if (resena.isPresent()) {
            return ResponseEntity.status(200).body("Reseña actualizada exitosamente");
        }
        return ResponseEntity.status(400).body("La reseña que indica no ha sido encontrada");
    }

    @DeleteMapping("/eliminar/{idResena}")
    public ResponseEntity<String> eliminarResena(@PathVariable Integer idResena) {
        Optional<Resena> resena = service.buscarPorIdResena(idResena);
        if (resena.isPresent()) {
            service.eliminarResena(idResena);
            return ResponseEntity.status(200).body("Reseña eliminada con éxito");
        }
        return ResponseEntity.status(400).body("La reseña que indica no ha sido encontrada");
    }

    @GetMapping("/listadoDTO")
    public List<ResenaListadoDTO> obtenerListado() {
        return service.listarDTO();
    }

    @GetMapping("/simpleDTO")
    public List<ResenaSimpleDTO> obtenerSimple() {
        return service.listarSimpleDTO();
    }

    @GetMapping("/{idResena}/detalleDTO")
    public ResponseEntity<ResenaDetalleDTO> obtenerDetalle(@PathVariable Integer idResena) {
        ResenaDetalleDTO dto = service.obtenerDetalleDTO(idResena);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
