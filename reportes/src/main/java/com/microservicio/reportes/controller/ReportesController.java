package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Pago;
import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.service.ReportesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private final ReportesService service;

    ReportesController(ReportesService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos los reportes",
            description = "Obtiene la lista completa de todos los reportes almacenados en el sistema",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<Reportes> listarTodosLosReportes() {
        return service.listarTodosLosReportes();
    }

    @Operation(
            summary = "Buscar reporte por ID",
            description = "Obtiene un reporte específico que coincide con el ID ingresado",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar-por-id/{idReporte}")
    public ResponseEntity<Reportes> buscarReportePorId(@PathVariable Integer idReporte) {
        return service.buscarReportePorId(idReporte)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Agregar un nuevo reporte",
            description = "Crea y registra un nuevo reporte validando los datos ingresados",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Formato de datos o fecha inválido"),
            @ApiResponse(responseCode = "404", description = "Error al crear el reporte"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarReporte(@Valid @RequestBody Reportes reporte) {
        Reportes nuevoReporte = service.agregarReporte(reporte);
        if (nuevoReporte != null) {

            return ResponseEntity.status(201).body("Reporte creado exitosamente: " + nuevoReporte.getIdReporte());
        } else {
            return ResponseEntity.status(404).body("Error al crear el reporte.");
        }
    }

    @Operation(
            summary = "Eliminar un reporte por ID",
            description = "Elimina de forma permanente el reporte que coincide con el ID ingresado",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{idReporte}")
    public ResponseEntity<String> eliminarReporte(@PathVariable Integer idReporte) {
        boolean eliminado = service.eliminarReporte(idReporte);
        if (eliminado) {
            return ResponseEntity.status(200).body("Reporte eliminado exitosamente.");
        } else {
            return ResponseEntity.status(404).body("Reporte no encontrado.");
        }
    }

    @Operation(
            summary = "Ver los pagos de un reporte",
            description = "Obtiene la lista de pagos asociados al ID del reporte ingresado",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}/ver-pagos")
    public ResponseEntity<List<Pago>> verPagosDelReporte(@PathVariable Integer id) {
        List<Pago> pagos = service.obtenerPagos(id);
        return ResponseEntity.ok(pagos);
    }
}