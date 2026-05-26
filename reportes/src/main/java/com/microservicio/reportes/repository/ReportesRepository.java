package com.microservicio.reportes.repository;

import com.microservicio.reportes.model.Reportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportesRepository extends JpaRepository<Reportes, Integer> {

    Optional<Reportes> findByIdReporte(Integer idReporte);

    List<Reportes> findByNombreReporte(String nombreReporte);

    List<Reportes> findByTipoReporte(String tipoReporte);

    List<Reportes> findByFechaInicioBetween(String fechaInicio, String fechaFin);

}
