package com.microservicio.reportes.service;

import com.microservicio.reportes.model.Pago;
import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.repository.ReportesRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ReportesService {

    private final ReportesRepository repository;

    ReportesService(ReportesRepository repository) {
        this.repository = repository;
    }

    public List<Reportes> listarTodosLosReportes() {
        return repository.findAll();
    }

    public Optional<Reportes> buscarReportePorId(Integer idReporte) {
        return repository.findByIdReporte(idReporte);
    }

    public List<Reportes> buscarReportePorNombre(String nombreReporte) {
        return repository.findByNombreReporte(nombreReporte);
    }

    public Reportes agregarReporte(Reportes reporte) {
        return repository.save(reporte);
    }

    public Optional<Reportes> actualizarReporte(Integer idReporte, Reportes reporteActualizado) {
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
        if(repository.existsById(idReporte)) {
            repository.deleteById(idReporte);
            return true;
        } else{
            return false;
        }
    }

    public List<Pago> obtenerPagos(Integer idReporte) {
        Reportes reporte = repository.findByIdReporte(idReporte).orElse(null);
        
        if (reporte == null) {
            return new ArrayList<>();
        }
        
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8084/carrito/pagos/rango-fechas?inicio=" 
                     + reporte.getFechaInicio() + "&fin=" + reporte.getFechaFin();

        try {
            Pago[] pagos = restTemplate.getForObject(url, Pago[].class);
            return pagos != null ? Arrays.asList(pagos) : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}



