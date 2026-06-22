package com.microservicio.promociones.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.promociones.model.Promociones;

public interface PromocionesRepository extends JpaRepository<Promociones, Integer> {

    Optional<Promociones> findByIdPromocion(Integer idPromocion);

    List<Promociones> findByFechaInicioBeforeAndFechaFinAfter(LocalDate fechaInicio, LocalDate fechaFin);

    Optional<Promociones> findByCodigoPromocional(String codigoPromocional);

}
