package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Pago;
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

    @PostMapping("/agregar")
    public ResponseEntity<Reportes> agregarReporte(@Valid @RequestBody Reportes reporte) {
        Reportes nuevoReporte = service.agregarReporte(reporte);
        return ResponseEntity.status(201).body(nuevoReporte);
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

    @GetMapping("/{id}/ver-pagos")
    public List<Pago> verPagosDelReporte(@PathVariable Integer id) {
        return service.obtenerPagos(id);
    }
}