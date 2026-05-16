package com.microsrvicio.promociones.repository;

import com.microsrvicio.promociones.model.Promociones;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionesRepository extends JpaRepository<Promociones, Integer> {

    Optional<Promociones> findByIdPromocion(Integer idPromocion);

    List<Promociones> findByNombrePromocionContainingIgnoreCase(String nombrePromocion);

    Optional<Promociones> findByCodigoPromocional(String codigoPromocional);

    List<Promociones> findByFechaInicioAfter(LocalDate fechaInicio);

    List<Promociones> findByFechaFinBefore(LocalDate fechaFin);

    List<Promociones> findByBetweenFechaInicioAndFechaFin(LocalDate fechaInicio, LocalDate fechaFin);

    List<Promociones> findByMontoMinimoGreaterThan(BigDecimal montoMinimo);

    List<Promociones> findByVecesUsoLessThan(Integer vecesUso);

    List<Promociones> findByDescuentoGreaterThan(BigDecimal descuento);

    List<Promociones> findByDescuentoLessThan(BigDecimal descuento);

}
