package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private ReportesService service;

    @GetMapping("/listar")
    public List<Reportes> listarTodosLosReportes() {
        return service.listarTodosLosReportes();
    }

    @GetMapping("/buscar-por-id/{idReporte}")
    public Optional<Reportes> buscarReportePorId(@PathVariable Integer idReporte) {
        return service.buscarReportePorId(idReporte);
    }

    @GetMapping("/buscar-por-nombre/{nombreReporte}")
    public List<Reportes> buscarReportePorNombre(@PathVariable String nombreReporte) {
        return service.buscarReportePorNombre(nombreReporte);
    }

    @GetMapping("/buscar-por-tipo/{tipoReporte}")
    public List<Reportes> buscarReportePorTipo(@PathVariable String tipoReporte) {
        return service.buscarReportePorTipo(tipoReporte);
    }

    @GetMapping("/buscar-por-fecha")
    public List<Reportes> buscarReportePorFecha(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        return service.buscarReportePorFecha(fechaInicio, fechaFin);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Reportes> agregarReporte(@Valid @RequestBody Reportes reporte) {
        Reportes nuevoReporte = service.agregarReporte(reporte);
        return ResponseEntity.status(201).body(nuevoReporte);
    }

    @PutMapping("/actualizar/{idReporte}")
    public ResponseEntity<String> actualizarReporte(@PathVariable Integer idReporte, @Valid @RequestBody Reportes reporteActualizado) {
        Optional<Reportes> reporteExistente = service.buscarReportePorId(idReporte);
        if (reporteExistente.isEmpty()) {
            return ResponseEntity.status(404).body("Reporte no encontrado.");
        }
        service.actualizarReporte(idReporte, reporteActualizado);
        return ResponseEntity.status(200).body("Reporte actualizado exitosamente.");
    }

    @DeleteMapping("/eliminar/{idReporte}")
    public ResponseEntity<String> eliminarReporte(@PathVariable Integer idReporte) {
        boolean eliminado = service.eliminarReporte(idReporte);
        if (eliminado) {
            return ResponseEntity.status(200).body("Reporte eliminado exitosamente.");
        } else {
            return ResponseEntity.status(404).body("Reporte no encontrado.");
        }
    }

    

}