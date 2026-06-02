package com.microservicio.promociones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.promociones.dto.PromocionesSimpleDTO;
import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.repository.PromocionesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PromocionesService {

    @Autowired
    private PromocionesRepository repository;

    public List<Promociones> listarPromociones() {
        return repository.findAll();
    }

    public Optional<Promociones> buscarPorId(Integer idPromocion) {
        return repository.findByIdPromocion(idPromocion);
    }

    public List<Promociones> buscarPorNombre(String nombrePromocion) {
        return repository.findByNombrePromocionContainingIgnoreCase(nombrePromocion);
    }

    public Optional<Promociones> buscarPorCodigo(String codigoPromocional) {
        return repository.findByCodigoPromocional(codigoPromocional);
    }

    public List<Promociones> buscarPorFechaInicio(LocalDate fechaInicio) {
        return repository.findByFechaInicioAfter(fechaInicio);
    }

    public List<Promociones> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaInicioBeforeAndFechaFinAfter(fechaInicio, fechaFin);
    }

    public List<Promociones> buscarPorFechaFin(LocalDate fechaFin) {
        return repository.findByFechaFinBefore(fechaFin);
    }

    public List<Promociones> buscarPorMontoMinimoMayor(BigDecimal montoMinimo) {
        return repository.findByMontoMinimoGreaterThan(montoMinimo);
    }

    public List<Promociones> buscarPorMontoMinimoMenor(BigDecimal montoMinimo) {
        return repository.findByMontoMinimoLessThan(montoMinimo);
    }

    public List<Promociones> buscarPorDescuentoMayor(BigDecimal descuento) {
        return repository.findByDescuentoGreaterThan(descuento);
    }

    public List<Promociones> buscarPorDescuentoMenor(BigDecimal descuento) {
        return repository.findByDescuentoLessThan(descuento);
    }

    public Promociones agregarPromocion(Promociones promocion) {
        return repository.save(promocion);
    }

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

    public boolean eliminarPromocion(Integer idPromocion) {
        if(repository.existsById(idPromocion)) {
            repository.deleteById(idPromocion);
            return true;
        }
    return false;
    }

    public BigDecimal aplicarPromocion(String codigoPromocional, BigDecimal montoCompra){
        Promociones promocion = repository.findByCodigoPromocional(codigoPromocional)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(promocion.getFechaInicio()) || hoy.isAfter(promocion.getFechaFin())) {
            throw new RuntimeException("La promoción no está activa");
        }
        if (promocion.getVecesUso() <= 0) {
            throw new RuntimeException("La promoción ha alcanzado su límite de uso");
        }
        if (montoCompra.compareTo(promocion.getMontoMinimo()) < 0) {
            throw new RuntimeException("El monto de compra no cumple con el monto mínimo requerido");
        }
        promocion.setVecesUso(promocion.getVecesUso() - 1);
        repository.save(promocion);
        BigDecimal factorDescuento = promocion.getDescuento().divide(new BigDecimal("100"));
        BigDecimal factorMontoFinal = BigDecimal.ONE.subtract(factorDescuento);
        return montoCompra.multiply(factorMontoFinal);
    }

    public List<PromocionesSimpleDTO> listarPromocionesSimpleDTO() {

    List<Promociones> promociones = repository.findAll();
    List<PromocionesSimpleDTO> promocionesSimpleDTO = new ArrayList<>();
    
    for(Promociones promocion : promociones){
        PromocionesSimpleDTO dto = new PromocionesSimpleDTO();
        dto.setNombrePromocion(promocion.getNombrePromocion());
        dto.setCodigoPromocional(promocion.getCodigoPromocional());
        dto.setDescuento(promocion.getDescuento());
        promocionesSimpleDTO.add(dto);
    }
    return promocionesSimpleDTO;
    }
}


