package com.microsrvicio.promociones.repository;

import com.microsrvicio.promociones.model.Promociones;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionesRepository extends JpaRepository<Promociones, Integer> {

    Optional<Promociones> findByIdPromocion(Integer idPromocion);

    List<Promociones> findByNombrePromocionContainingIgnoreCase(String nombrePromocion);

    

}
