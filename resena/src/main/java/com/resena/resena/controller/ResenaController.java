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

    @Operation(
            summary = "Buscar reseña por ID", description = "Obtiene una reseña específica a partir de su identificador", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reseña")
    })
    @GetMapping("/resena/{idResena}")
    public ResponseEntity<Resena> buscarPorIdResena(@PathVariable Integer idResena) {
        Optional<Resena> resena = service.buscarPorIdResena(idResena);
        return resena.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Buscar reseñas por usuario", description = "Obtiene todas las reseñas asociadas a un usuario", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseñas del usuario obtenidas correctamente")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Resena>> buscarPorIdUsuario(@PathVariable Integer idUsuario) {
        List<Resena> resenas = service.buscarPorIdUsuario(idUsuario);
        return ResponseEntity.ok(resenas);
    }

    @Operation(
            summary = "Buscar reseñas por producto", description = "Obtiene todas las reseñas asociadas a un producto", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseñas del producto obtenidas correctamente")
    })
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<Resena>> buscarPorIdProducto(@PathVariable Integer idProducto) {
        List<Resena> resenas = service.buscarPorIdProducto(idProducto);
        return ResponseEntity.ok(resenas);
    }

    @Operation(
            summary = "Agregar una reseña", description = "Crea una nueva reseña en el sistema", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario o producto asociado"),
            @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping("/agregarResena")
    public ResponseEntity<Resena> agregarResena(@Valid @RequestBody Resena resena) {
        Resena nuevaResena = service.agregarResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
    }

    @Operation(
            summary = "Actualizar reseña", description = "Actualiza los datos de una reseña existente", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "La reseña no fue encontrada o la solicitud es inválida"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reseña")
    })
    @PutMapping("/actualizar/{idResena}")
    public ResponseEntity<String> actualizarResena(@PathVariable Integer idResena, @RequestBody Resena resenaActualizada) {
        Optional<Resena> resena = service.resenaUpdate(idResena, resenaActualizada);
        if (resena.isPresent()) {
            return ResponseEntity.status(200).body("Reseña actualizada exitosamente");
        }
        return ResponseEntity.status(400).body("La reseña que indica no ha sido encontrada");
    }

    @Operation(
            summary = "Eliminar reseña", description = "Elimina una reseña existente a partir de su ID", tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "La reseña no fue encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reseña")
    })
    @DeleteMapping("/eliminar/{idResena}")
    public ResponseEntity<String> eliminarResena(@PathVariable Integer idResena) {
        Optional<Resena> resena = service.buscarPorIdResena(idResena);
        if (resena.isPresent()) {
            service.eliminarResena(idResena);
            return ResponseEntity.status(200).body("Reseña eliminada con éxito");
        }
        return ResponseEntity.status(400).body("La reseña que indica no ha sido encontrada");
    }

    @Operation(
            summary = "Obtener listado DTO", description = "Obtiene una vista resumida de reseñas con información relevante", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente")
    })
    @GetMapping("/listadoDTO")
    public ResponseEntity<List<ResenaListadoDTO>>obtenerListado() {
        return ResponseEntity.ok(service.listarDTO());
    }

    @Operation(
            summary = "Obtener simple DTO", description = "Obtiene una vista simple de reseñas", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado simple obtenido correctamente")
    })
    @GetMapping("/simpleDTO")
    public ResponseEntity<List<ResenaSimpleDTO>> obtenerSimple() {
        return ResponseEntity.ok(service.listarSimpleDTO());
    }

    @Operation(
            summary = "Obtener detalle DTO", description = "Obtiene el detalle completo de una reseña en formato DTO", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de la reseña obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reseña")
    })
    @GetMapping("/{idResena}/detalleDTO")
    public ResponseEntity<ResenaDetalleDTO> obtenerDetalle(@PathVariable Integer idResena) {
        ResenaDetalleDTO dto = service.obtenerDetalleDTO(idResena);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
