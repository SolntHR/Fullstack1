package com.microsrvicio.promociones.service;

import com.microsrvicio.promociones.model.Promociones;
import com.microsrvicio.promociones.repository.PromocionesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromocionesService {

    @Autowired
    private PromocionesRepository repository;

    // OBTENER TODAS LAS PROMOCIONES
    public List<Promociones> listarPromociones() {
        return repository.findAll();
    }

    // OBTENER PROMOCION POR ID
    public Optional<Promociones> buscarPorId(Integer idPromocion) {
        return repository.findByIdPromocion(idPromocion);
    }

    // OBTENER PROMOCION POR NOMBRE
    public List<Promociones> buscarPorNombre(String nombrePromocion) {
        return repository.findByNombrePromocionContainingIgnoreCase(nombrePromocion);
    }

    // OBTENER PROMOCION POR CODIGO PROMOCIONAL
    public Optional<Promociones> buscarPorCodigo(String codigoPromocional) {
        return repository.findByCodigoPromocional(codigoPromocional);
    }

    // OBTENER PROMOCIONES POR FECHA DE INICIO
    public List<Promociones> buscarPorFechaInicio(LocalDate fechaInicio) {
        return repository.findByFechaInicioAfter(fechaInicio);
    }

    // OBTENER PROMOCIONES POR RANGO DE FECHAS
    public List<Promociones> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByBetweenFechaInicioAndFechaFin(fechaInicio, fechaFin);
    }

    // OBTENER PROMOCIONES POR FECHA DE FIN
    public List<Promociones> buscarPorFechaFin(LocalDate fechaFin) {
        return repository.findByFechaFinBefore(fechaFin);
    }

    // OBTENER PROMOCIONES POR MONTO MINIMO
    public List<Promociones> buscarPorMontoMinimo(BigDecimal montoMinimo) {
        return repository.findByMontoMinimoGreaterThan(montoMinimo);
    }

    // OBTENER PROMOCIONES POR VECES DE USO
    public List<Promociones> buscarPorVecesUso(Integer vecesUso) {  
        return repository.findByVecesUsoLessThan(vecesUso);
    }

    // OBTENER PROMOCIONES POR DESCUENTO MAYOR A
    public List<Promociones> buscarPorDescuentoMayor(BigDecimal descuento) {
        return repository.findByDescuentoGreaterThan(descuento);
    }

    // OBTENER PROMOCIONES POR DESCUENTO MENOR A
    public List<Promociones> buscarPorDescuentoMenor(BigDecimal descuento) {
        return repository.findByDescuentoLessThan(descuento);
    }

    /*------------------------------------------------------------------------*/

    // CREAR NUEVA PROMOCION

    public Promociones crearPromocion(Promociones promocion) {
        return repository.save(promocion);
    }

    // ACTUALIZAR PROMOCION EXISTENTE
    public Optional<Promociones> actualizarPromocion(Integer idPromocion, Promociones promocionActualizada){
        return repository.findByIdPromocion(idPromocion).map(promocion -> {
            promocion.setNombrePromocion(promocionActualizada.getNombrePromocion());
            promocion.setCodigoPromocional(promocionActualizada.getCodigoPromocional());
            promocion.setDescuento(promocionActualizada.getDescuento());
            promocion.setFechaInicio(promocionActualizada.getFechaInicio());
            promocion.setFechaFin(promocionActualizada.getFechaFin());
            promocion.setVecesUso(promocionActualizada.getVecesUso());
            promocion.setMontoMinimo(promocionActualizada.getMontoMinimo());
            return repository.save(promocion);
        });

    }

    // ELIMINAR PROMOCION POR ID
    public boolean eliminarPromocion(Integer idPromocion) {
        if(repository.existsById(idPromocion)) {
            repository.deleteById(idPromocion);
            return true;
        }
    return false;
    }
   
}
