package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Pago;
import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.service.ReportesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private final ReportesService service;

    ReportesController(ReportesService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos los reportes",
            description = "Obtiene la lista completa de todos los reportes almacenados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<Reportes> listarTodosLosReportes() {
        log.info("Recibida petición GET /reportes/listar");
        return service.listarTodosLosReportes();
    }

    @Operation(
            summary = "Buscar reporte por ID",
            description = "Obtiene un reporte específico que coincide con el ID ingresado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-por-id/{idReporte}")
    public ResponseEntity<Reportes> buscarReportePorId(@PathVariable Integer idReporte) {
        log.info("Recibida petición GET /reportes/buscar-por-id/{}", idReporte);
        return service.buscarReportePorId(idReporte)
                .map(reporte -> {
                    log.info("Reporte con ID {} encontrado", idReporte);
                    return ResponseEntity.ok(reporte);
                })
                .orElseGet(() -> {
                    log.warn("Reporte con ID {} no encontrado", idReporte);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(
            summary = "Agregar un nuevo reporte",
            description = "Crea y registra un nuevo reporte validando los datos ingresados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Formato de datos o fecha inválido"),
            @ApiResponse(responseCode = "404", description = "Error al crear el reporte"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarReporte(@Valid @RequestBody Reportes reporte) {
        log.info("Recibida petición POST /reportes/agregar con cuerpo: {}", reporte);
        Reportes nuevoReporte = service.agregarReporte(reporte);
        if (nuevoReporte != null) {
            log.info("Reporte creado exitosamente con ID: {}", nuevoReporte.getIdReporte());
            return ResponseEntity.status(201).body("Reporte creado exitosamente: " + nuevoReporte.getIdReporte());
        } else {
            log.error("Error al intentar crear el reporte. El servicio retornó null.");
            return ResponseEntity.status(404).body("Error al crear el reporte.");
        }
    }

    @Operation(
            summary = "Eliminar un reporte por ID",
            description = "Elimina de forma permanente el reporte que coincide con el ID ingresado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{idReporte}")
    public ResponseEntity<String> eliminarReporte(@PathVariable Integer idReporte) {
        log.info("Recibida petición DELETE /reportes/eliminar/{}", idReporte);
        boolean eliminado = service.eliminarReporte(idReporte);
        if (eliminado) {
            log.info("Reporte con ID {} eliminado exitosamente desde el controlador", idReporte);
            return ResponseEntity.status(200).body("Reporte eliminado exitosamente.");
        } else {
            log.warn("No se pudo eliminar el reporte con ID {} porque no existe", idReporte);
            return ResponseEntity.status(404).body("Reporte no encontrado.");
        }
    }

    @Operation(
            summary = "Ver los pagos de un reporte",
            description = "Obtiene la lista de pagos asociados al ID del reporte ingresado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}/ver-pagos")
    public ResponseEntity<List<Pago>> verPagosDelReporte(@PathVariable Integer id) {
        log.info("Recibida petición GET /reportes/{}/ver-pagos", id);
        List<Pago> pagos = service.obtenerPagos(id);
        log.info("Se recuperaron {} pagos para el reporte con ID {}", pagos.size(), id);
        return ResponseEntity.ok(pagos);
    }
}