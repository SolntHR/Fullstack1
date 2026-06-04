package com.microservicio.reportes;

import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.model.TipoReporte;
import com.microservicio.reportes.repository.ReportesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ReportesRepository repository) {
        return args -> {

            crearReporteSiNoExiste(repository, 
                "Ventas Junio 2026", 
                "Reporte detallado de ventas del mes de Junio", 
                TipoReporte.VENTAS_MENSUAL, 
                "2026-06-01", 
                "2026-06-30");

            crearReporteSiNoExiste(repository, 
                "Balance Anual 2025", 
                "Consolidado de ingresos del año 2025", 
                TipoReporte.VENTAS_ANUAL, 
                "2025-01-01", 
                "2025-12-31");

            crearReporteSiNoExiste(repository, 
                "Reporte Cyber Mayo", 
                "Seguimiento de ventas durante el periodo Cyber", 
                TipoReporte.VENTAS_MENSUAL, 
                "2026-05-25", 
                "2026-05-28");
        };
    }

    private void crearReporteSiNoExiste(ReportesRepository repo, 
                                        String nombre, 
                                        String descripcion, 
                                        TipoReporte tipo, 
                                        String fechaInicio, 
                                        String fechaFin) {
                                              
        if (repo.findByNombreReporte(nombre).isEmpty()) {
            Reportes nuevoReporte = new Reportes();
            nuevoReporte.setNombreReporte(nombre);
            nuevoReporte.setDescripcionReporte(descripcion);
            nuevoReporte.setTipoReporte(tipo);
            nuevoReporte.setFechaInicio(fechaInicio);
            nuevoReporte.setFechaFin(fechaFin);
            
            repo.save(nuevoReporte);
            System.out.println("Cargado reporte inicial: " + nombre);
        } else {
            System.out.println("El reporte '" + nombre + "' ya existe. No se cargará nuevamente.");
        }
    }
}