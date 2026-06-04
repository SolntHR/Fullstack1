package com.microservicio.promociones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.promociones.dto.PromocionesSimpleDTO;
import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.repository.PromocionesRepository;

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

    public List<Promociones> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaInicioBeforeAndFechaFinAfter(fechaInicio, fechaFin);
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


