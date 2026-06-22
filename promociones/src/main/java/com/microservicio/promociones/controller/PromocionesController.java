package com.microservicio.promociones.controller;

import com.microservicio.promociones.dto.PromocionesSimpleDTO;
import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.service.PromocionesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promociones")
@Slf4j
public class PromocionesController {

    private final PromocionesService service;

    public PromocionesController(PromocionesService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar promociones",
            description = "Obtiene la lista completa de promociones registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de promociones obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Promociones>> listarPromociones() {
        log.info("Solicitud recibida para listar promociones");
        return ResponseEntity.ok(service.listarPromociones());
    }

    @Operation(
            summary = "Buscar promoción por id",
            description = "Busca una promoción específica a partir de su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promoción encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Promoción no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar/{idPromocion}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer idPromocion) {
        log.info("Solicitud recibida para buscar promoción con id: {}", idPromocion);

        Optional<Promociones> promocion = service.buscarPorId(idPromocion);

        if (promocion.isPresent()) {
            log.info("Promoción encontrada en controller con id: {}", idPromocion);
            return ResponseEntity.ok(promocion.get());
        }

        log.warn("Promoción no encontrada en controller con id: {}", idPromocion);
        return ResponseEntity.status(404).body("Promoción no encontrada");
    }

    @Operation(
            summary = "Buscar promociones por rango de fechas",
            description = "Obtiene la lista de promociones vigentes dentro de un rango de fechas determinado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Formatos de fecha inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-rango-fechas")
    public ResponseEntity<List<Promociones>> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        log.info("Solicitud recibida para buscar promociones entre {} y {}", fechaInicio, fechaFin);
        return ResponseEntity.ok(service.buscarPorRangoFechas(fechaInicio, fechaFin));
    }

    @Operation(
            summary = "Crear promoción",
            description = "Registra una nueva promoción en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Promoción creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarPromocion(@Valid @RequestBody Promociones promocion) {
        log.info("Solicitud recibida para crear promoción: {}", promocion.getNombrePromocion());
        Promociones nuevaPromocion = service.agregarPromocion(promocion);
        log.info("Promoción creada correctamente con id: {}", nuevaPromocion.getIdPromocion());
        return ResponseEntity.status(201).body("Promoción creada exitosamente con el id: " + nuevaPromocion.getIdPromocion());
    }

    @Operation(
            summary = "Actualizar promoción",
            description = "Actualiza los datos de una promoción existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promoción actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Promoción no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{idPromocion}")
    public ResponseEntity<String> actualizarPromocion(@PathVariable Integer idPromocion,
                                                      @Valid @RequestBody Promociones promocionActualizada) {
        log.info("Solicitud recibida para actualizar promoción con id: {}", idPromocion);

        Optional<Promociones> promocionExistente = service.buscarPorId(idPromocion);

        if (promocionExistente.isPresent()) {
            service.actualizarPromocion(idPromocion, promocionActualizada);
            log.info("Promoción actualizada correctamente con id: {}", idPromocion);
            return ResponseEntity.ok("Promoción actualizada exitosamente");
        }

        log.warn("No se pudo actualizar. Promoción no encontrada con id: {}", idPromocion);
        return ResponseEntity.status(404).body("No se pudo encontrar el id de la promoción");
    }

    @Operation(
            summary = "Eliminar promoción",
            description = "Elimina una promoción existente según su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promoción personalizada eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Promoción no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{idPromocion}")
    public ResponseEntity<String> eliminarPromocion(@PathVariable Integer idPromocion) {
        log.info("Solicitud recibida para eliminar promoción con id: {}", idPromocion);

        Optional<Promociones> promocionExistente = service.buscarPorId(idPromocion);

        if (promocionExistente.isPresent()) {
            service.eliminarPromocion(idPromocion);
            log.info("Promoción eliminada correctamente con id: {}", idPromocion);
            return ResponseEntity.ok("Promoción eliminada exitosamente");
        }

        log.warn("No se pudo eliminar. Promoción no encontrada con id: {}", idPromocion);
        return ResponseEntity.status(404).body("No se pudo encontrar el id de la promoción");
    }

    @Operation(
            summary = "Obtener listado simple DTO",
            description = "Obtiene la lista de promociones en un formato resumido simplificado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar-dto")
    public ResponseEntity<List<PromocionesSimpleDTO>> listarPromocionesSimpleDTO() {
        log.info("Solicitud recibida para listar promociones en formato DTO");
        return ResponseEntity.ok(service.listarPromocionesSimpleDTO());
    }
}