package com.envio.despacho.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.envio.despacho.dto.VerificacionDetalladoDTO;
import com.envio.despacho.dto.VerificacionListadoDTO;
import com.envio.despacho.dto.VerificacionSimpleDTO;
import com.envio.despacho.exception.Excepcion;
import com.envio.despacho.model.Verificacion;
import com.envio.despacho.repository.VerificacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificacionService {

    private final VerificacionRepository verificacionRepository;

    public List<Verificacion> listar() {
        return verificacionRepository.findAll();
    }

    public Verificacion buscar(Integer idVerificacion) {
        return verificacionRepository.findById(idVerificacion)
                .orElseThrow(() ->
                    new Excepcion(
                        "Verificación de despacho no encontrada con ID: "
                        + idVerificacion));
    }

    public Verificacion guardar(Verificacion verificacion) {
        return verificacionRepository.save(verificacion);
    }

    public Verificacion actualizar(Integer idVerificacion, Verificacion verificacion) {
        Verificacion existente =
                verificacionRepository.findById(idVerificacion)
                .orElseThrow(() ->
                    new Excepcion(
                        "Verificación de despacho no encontrada con ID: "
                        + idVerificacion));

        existente.setIdEnvio(verificacion.getIdEnvio());
        existente.setFechaEntrega(verificacion.getFechaEntrega());
        existente.setEstadoEntrega(verificacion.getEstadoEntrega());
        existente.setObservacion(verificacion.getObservacion());

        return verificacionRepository.save(existente);
    }

    public void eliminar(Integer idVerificacion) {
        Verificacion existente =
                verificacionRepository.findById(idVerificacion)
                .orElseThrow(() ->
                    new Excepcion(
                        "Verificación de despacho no encontrada con ID: "
                        + idVerificacion));

        verificacionRepository.delete(existente);
    }

    public List<VerificacionListadoDTO> listarDTO() {
        List<VerificacionListadoDTO> listaDTO = new ArrayList<>();

        for (Verificacion verificacion : verificacionRepository.findAll()) {
            VerificacionListadoDTO dto = new VerificacionListadoDTO();
            dto.setIdVerificacion(verificacion.getIdVerificacion());
            dto.setFechaEntrega(verificacion.getFechaEntrega());
            dto.setEstadoEntrega(verificacion.getEstadoEntrega());
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public List<VerificacionSimpleDTO> listarSimpleDTO() {
        List<VerificacionSimpleDTO> listaDTO = new ArrayList<>();

        for (Verificacion verificacion : verificacionRepository.findAll()) {
            VerificacionSimpleDTO dto = new VerificacionSimpleDTO();
            dto.setIdVerificacion(verificacion.getIdVerificacion());
            dto.setEstadoEntrega(verificacion.getEstadoEntrega());
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public VerificacionDetalladoDTO obtenerDetalleDTO(Integer idVerificacion) {
        Verificacion verificacion = verificacionRepository.findById(idVerificacion)
                .orElseThrow(() ->
                    new Excepcion(
                        "Verificación de despacho no encontrada con ID: "
                        + idVerificacion));

        VerificacionDetalladoDTO dto = new VerificacionDetalladoDTO();
        dto.setIdVerificacion(verificacion.getIdVerificacion());
        dto.setIdEnvio(verificacion.getIdEnvio());
        dto.setFechaEntrega(verificacion.getFechaEntrega());
        dto.setEstadoEntrega(verificacion.getEstadoEntrega());
        dto.setObservacion(verificacion.getObservacion());

        return dto;
    }
}
