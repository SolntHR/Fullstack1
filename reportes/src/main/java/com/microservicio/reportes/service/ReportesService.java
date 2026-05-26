package com.microservicio.reportes.service;

import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.repository.ReportesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class ReportesService {

    @Autowired
    private ReportesRepository repository;

    public List<Reportes> listarTodosLosReportes() {
        return repository.findAll();
    }

    public Optional<Reportes> buscarReportePorId(Integer idReporte) {
        return repository.findByIdReporte(idReporte);
    }

    public List<Reportes> buscarReportePorNombre(String nombreReporte) {
        return repository.findByNombreReporte(nombreReporte);
    }

    public List<Reportes> buscarReportePorTipo(String tipoReporte) {
        return repository.findByTipoReporte(tipoReporte);
    }

    public List<Reportes> buscarReportePorFecha(String fechaInicio, String fechaFin) {
        return repository.findByFechaInicioBetween(fechaInicio, fechaFin);
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

}
