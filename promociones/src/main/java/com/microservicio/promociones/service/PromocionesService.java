package com.microservicio.promociones.service;

import com.microservicio.promociones.dto.PromocionesSimpleDTO;
import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.repository.PromocionesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PromocionesService {

    private final PromocionesRepository repository;

    public PromocionesService(PromocionesRepository repository) {
        this.repository = repository;
    }

    public List<Promociones> listarPromociones() {
        log.info("Listando todas las promociones");
        return repository.findAll();
    }

    public Optional<Promociones> buscarPorId(Integer idPromocion) {
        log.info("Buscando promoción con id: {}", idPromocion);
        Optional<Promociones> promocion = repository.findById(idPromocion);

        if (promocion.isPresent()) {
            log.info("Promoción encontrada con id: {}", idPromocion);
        } else {
            log.warn("No se encontró promoción con id: {}", idPromocion);
        }

        return promocion;
    }

    public List<Promociones> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Buscando promociones vigentes entre el rango de fechas: {} y {}", fechaInicio, fechaFin);
        return repository.findByFechaInicioBeforeAndFechaFinAfter(fechaInicio, fechaFin);
    }

    public Promociones agregarPromocion(Promociones promocion) {
        log.info("Intentando registrar promoción con código: {}", promocion.getCodigoPromocional());

        // Modifica el método de tu repositorio según corresponda si usas validación por código único
        if (repository.findByCodigoPromocional(promocion.getCodigoPromocional()).isPresent()) {
            log.warn("Intento de registro con código promocional ya existente: {}", promocion.getCodigoPromocional());
            throw new IllegalArgumentException("El código promocional ya está registrado");
        }

        try {
            Promociones promocionGuardada = repository.save(promocion);
            log.info("Promoción creada exitosamente con id: {}", promocionGuardada.getIdPromocion());
            return promocionGuardada;
        } catch (Exception e) {
            log.error("Error al crear promoción con código: {}", promocion.getCodigoPromocional(), e);
            throw new RuntimeException("Error al crear la promoción");
        }
    }

    public Optional<Promociones> actualizarPromocion(Integer idPromocion, Promociones promocionActualizada) {
        log.info("Intentando actualizar promoción con id: {}", idPromocion);

        return repository.findById(idPromocion).map(promocion -> {
            Optional<Promociones> promocionConMismoCodigo = repository.findByCodigoPromocional(promocionActualizada.getCodigoPromocional());

            if (promocionConMismoCodigo.isPresent() && 
                    !promocionConMismoCodigo.get().getIdPromocion().equals(idPromocion)) {
                log.warn("Intento de actualizar promoción con código ya existente: {}", promocionActualizada.getCodigoPromocional());
                throw new IllegalArgumentException("Ya existe una promoción con ese código promocional");
            }

            try {
                promocion.setNombrePromocion(promocionActualizada.getNombrePromocion());
                promocion.setCodigoPromocional(promocionActualizada.getCodigoPromocional());
                promocion.setDescuento(promocionActualizada.getDescuento());
                promocion.setFechaInicio(promocionActualizada.getFechaInicio());
                promocion.setFechaFin(promocionActualizada.getFechaFin());
                promocion.setVecesUso(promocionActualizada.getVecesUso());
                promocion.setMontoMinimo(promocionActualizada.getMontoMinimo());

                Promociones guardada = repository.save(promocion);
                log.info("Promoción actualizada exitosamente con id: {}", guardada.getIdPromocion());
                return guardada;
            } catch (Exception e) {
                log.error("Error al actualizar promoción con id: {}", idPromocion, e);
                throw new RuntimeException("Error al actualizar la promoción");
            }
        });
    }

    public boolean eliminarPromocion(Integer idPromocion) {
        log.info("Intentando eliminar promoción con id: {}", idPromocion);

        if (repository.existsById(idPromocion)) {
            repository.deleteById(idPromocion);
            log.info("Promoción eliminada exitosamente con id: {}", idPromocion);
            return true;
        }

        log.warn("No se pudo eliminar. Promoción no encontrada con id: {}", idPromocion);
        return false;
    }

    public List<PromocionesSimpleDTO> listarPromocionesSimpleDTO() {
        log.info("Obteniendo listado simplificado DTO de todas las promociones");

        List<Promociones> promociones = repository.findAll();
        List<PromocionesSimpleDTO> promocionesSimpleDTO = new ArrayList<>();

        for (Promociones promocion : promociones) {
            PromocionesSimpleDTO dto = new PromocionesSimpleDTO();
            dto.setNombrePromocion(promocion.getNombrePromocion());
            dto.setCodigoPromocional(promocion.getCodigoPromocional());
            dto.setDescuento(promocion.getDescuento());
            promocionesSimpleDTO.add(dto);
        }

        log.info("Listado simplificado DTO generado con {} elementos", promocionesSimpleDTO.size());
        return promocionesSimpleDTO;
    }
}