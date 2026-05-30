package com.envio.despacho.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.envio.despacho.model.Verificacion;
import com.envio.despacho.repository.VerificacionRepository;
import lombok.RequiredArgsConstructor;
import com.envio.despacho.exception.Excepcion;

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

    public Verificacion guardar(
            Verificacion verificacion) {

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
}
