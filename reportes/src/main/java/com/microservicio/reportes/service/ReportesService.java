package com.microservicio.reportes.service;

import com.microservicio.reportes.model.Pago;
import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.repository.ReportesRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReportesService {

    private final ReportesRepository repository;
    @Value("${carrito.service.url}")
    private String carritoServiceUrl;

    ReportesService(ReportesRepository repository) {
        this.repository = repository;
    }

    public List<Reportes> listarTodosLosReportes() {
        log.info("Listando todos los reportes");
        return repository.findAll();
    }

    public Optional<Reportes> buscarReportePorId(Integer idReporte) {
        log.info("Buscando reporte por ID: {}", idReporte);
        return repository.findByIdReporte(idReporte);
    }

    public List<Reportes> buscarReportePorNombre(String nombreReporte) {
        log.info("Buscando reporte por nombre: {}", nombreReporte);
        return repository.findByNombreReporte(nombreReporte);
    }

    public Reportes agregarReporte(Reportes reporte) {
        log.info("Agregando nuevo reporte: {}", reporte);
        return repository.save(reporte);
    }

    public Optional<Reportes> actualizarReporte(Integer idReporte, Reportes reporteActualizado) {
        log.info("Actualizando reporte con ID: {}", idReporte);
        return repository.findByIdReporte(idReporte).map(reporte -> {
            reporte.setNombreReporte(reporteActualizado.getNombreReporte());
            reporte.setDescripcionReporte(reporteActualizado.getDescripcionReporte());
            reporte.setTipoReporte(reporteActualizado.getTipoReporte());
            reporte.setFechaInicio(reporteActualizado.getFechaInicio());
            reporte.setFechaFin(reporteActualizado.getFechaFin());
            return repository.save(reporte);
        });
    }

    public boolean eliminarReporte(Integer idReporte) {
        log.info("Intentando eliminar reporte con ID: {}", idReporte);
        if(repository.existsById(idReporte)) {
            repository.deleteById(idReporte);
            log.info("Reporte con ID: {} eliminado", idReporte);
            return true;
        } else{
            log.warn("No se encontró el reporte con ID: {} para eliminar", idReporte);
            return false;
        }
    }

    public List<Pago> obtenerPagos(Integer idReporte) {
        log.info("Obteniendo pagos para el reporte ID: {}", idReporte);
        Reportes reporte = repository.findByIdReporte(idReporte).orElse(null);
        
        if (reporte == null) {
            log.warn("Reporte con ID: {} no encontrado", idReporte);
            return new ArrayList<>();
        }
        
        RestTemplate restTemplate = new RestTemplate();

        String url = carritoServiceUrl + "/carrito/pagos/rango-fechas?inicio="
                     + reporte.getFechaInicio() + "&fin=" + reporte.getFechaFin();

        try {
            log.info("Consultando pagos a la URL: {}", url);
            Pago[] pagos = restTemplate.getForObject(url, Pago[].class);
            return pagos != null ? Arrays.asList(pagos) : new ArrayList<>();
        } catch (Exception e) {
            log.error("Error al obtener pagos desde el microservicio de carrito para el reporte {}: {}", idReporte, e.getMessage());
            return new ArrayList<>();
        }
    }
}