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
        return repository.findByFechaInicioBeforeAndFechaFinAfter(fechaInicio, fechaFin);
    }

    // OBTENER PROMOCIONES POR FECHA DE FIN
    public List<Promociones> buscarPorFechaFin(LocalDate fechaFin) {
        return repository.findByFechaFinBefore(fechaFin);
    }

    // OBTENER PROMOCIONES POR MONTO MINIMO MAYOR A
    public List<Promociones> buscarPorMontoMinimoMayor(BigDecimal montoMinimo) {
        return repository.findByMontoMinimoGreaterThan(montoMinimo);
    }

    // OBTENER PROMOCIONES POR MONTO MINIMO MENOR A
    public List<Promociones> buscarPorMontoMinimoMenor(BigDecimal montoMinimo) {
        return repository.findByMontoMinimoLessThan(montoMinimo);
    }

    // OBTENER PROMOCIONES POR VECES DE USO MENOR A
    public List<Promociones> buscarPorVecesUsoMenor(Integer vecesUso) {  
        return repository.findByVecesUsoLessThan(vecesUso);
    }

    // OBTENER PROMOCIONES POR VECES DE USO MAYOR A
    public List<Promociones> buscarPorVecesUsoMayor(Integer vecesUso) {
        return repository.findByVecesUsoGreaterThan(vecesUso);
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

    // AGREGAR NUEVA PROMOCION

    public Promociones agregarPromocion(Promociones promocion) {
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

    // APLICAR PROMOCION
    // uso montoCOmpra por mientras se hace ese MS
    public BigDecimal aplicarPromocion(String codigoPromocional, BigDecimal montoCompra){
        // Buscar si existe la promocion
        Promociones promocion = repository.findByCodigoPromocional(codigoPromocional)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        // Validar si la promocion esta activa
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(promocion.getFechaInicio()) || hoy.isAfter(promocion.getFechaFin())) {
            throw new RuntimeException("La promoción no está activa");
        }
        // Validar si la promocion tiene veces de uso disponibles
        if (promocion.getVecesUso() <= 0) {
            throw new RuntimeException("La promoción ha alcanzado su límite de uso");
        }
        // Validar si el monto de compra cumple con el monto minimo
        if (montoCompra.compareTo(promocion.getMontoMinimo()) < 0) {
            throw new RuntimeException("El monto de compra no cumple con el monto mínimo requerido");
        }
        // Restar las veces de uso
        promocion.setVecesUso(promocion.getVecesUso() - 1);
        repository.save(promocion);
        // Convertir el porcentaje a factor decimal ej: 15.00 / 100 = 0.15
        BigDecimal factorDescuento = promocion.getDescuento().divide(new BigDecimal("100"));
        // Calcular cuánto paga el cliente ej: 1 - 0.15 = 0.85
        BigDecimal factorMontoFinal = BigDecimal.ONE.subtract(factorDescuento);
        // Devolver el precio final ej: $50.000 * 0.85 = $42.500
        return montoCompra.multiply(factorMontoFinal);
    }

    /*------------------------------------------------------------------------*/

    // GET: DTO
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


