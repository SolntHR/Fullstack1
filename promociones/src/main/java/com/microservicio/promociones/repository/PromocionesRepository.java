package com.microservicio.promociones.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.promociones.model.Promociones;

@Repository
public interface PromocionesRepository extends JpaRepository<Promociones, Integer> {

    Optional<Promociones> findByIdPromocion(Integer idPromocion);

    List<Promociones> findByNombrePromocionContainingIgnoreCase(String nombrePromocion);

    Optional<Promociones> findByCodigoPromocional(String codigoPromocional);

    List<Promociones> findByFechaInicioAfter(LocalDate fechaInicio);

    List<Promociones> findByFechaFinBefore(LocalDate fechaFin);

    List<Promociones> findByFechaInicioBeforeAndFechaFinAfter(LocalDate fechaInicio, LocalDate fechaFin);

    List<Promociones> findByMontoMinimoGreaterThan(BigDecimal montoMinimo);

    List<Promociones> findByMontoMinimoLessThan(BigDecimal montoMinimo);



    List<Promociones> findByDescuentoGreaterThan(BigDecimal descuento);

    List<Promociones> findByDescuentoLessThan(BigDecimal descuento);

}
