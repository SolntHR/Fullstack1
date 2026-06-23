package com.envio.despacho.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.envio.despacho.dto.VerificacionDetalladoDTO;
import com.envio.despacho.exception.Excepcion;
import com.envio.despacho.model.Verificacion;
import com.envio.despacho.repository.VerificacionRepository;

@ExtendWith(MockitoExtension.class)
class VerificacionServiceTest {

    @Mock
    private VerificacionRepository verificacionRepository;

    private VerificacionService verificacionService;

    @BeforeEach
    void setUp() {
        verificacionService = new VerificacionService(verificacionRepository);
    }

    @Test
    void listarDebeRetornarTodasLasVerificaciones() {
        Verificacion verificacion = crearVerificacionValida();
        when(verificacionRepository.findAll()).thenReturn(List.of(verificacion));

        List<Verificacion> resultado = verificacionService.listar();

        assertEquals(1, resultado.size());
        assertEquals("ENTREGADO", resultado.getFirst().getEstadoEntrega());
    }

    @Test
    void buscarDebeRetornarVerificacionCuandoExiste() {
        Verificacion verificacion = crearVerificacionValida();
        when(verificacionRepository.findById(1)).thenReturn(Optional.of(verificacion));

        Verificacion resultado = verificacionService.buscar(1);

        assertEquals(1, resultado.getIdEnvio());
    }

    @Test
    void buscarDebeLanzarExcepcionCuandoNoExiste() {
        when(verificacionRepository.findById(99)).thenReturn(Optional.empty());

        Excepcion exception = assertThrows(Excepcion.class, () -> verificacionService.buscar(99));

        assertEquals("Verificación de despacho no encontrada con ID: 99", exception.getMessage());
    }

    @Test
    void guardarDebePersistirVerificacion() {
        Verificacion verificacion = crearVerificacionValida();
        when(verificacionRepository.save(verificacion)).thenReturn(verificacion);

        Verificacion resultado = verificacionService.guardar(verificacion);

        assertEquals("ENTREGADO", resultado.getEstadoEntrega());
        verify(verificacionRepository).save(verificacion);
    }

    @Test
    void actualizarDebeModificarVerificacionExistente() {
        Verificacion existente = crearVerificacionValida();
        Verificacion actualizacion = new Verificacion();
        actualizacion.setIdEnvio(2);
        actualizacion.setFechaEntrega("2026-06-22");
        actualizacion.setEstadoEntrega("RECHAZADO");
        actualizacion.setObservacion("Cliente no disponible");

        when(verificacionRepository.findById(1)).thenReturn(Optional.of(existente));
        when(verificacionRepository.save(any(Verificacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Verificacion resultado = verificacionService.actualizar(1, actualizacion);

        assertEquals(2, resultado.getIdEnvio());
        assertEquals("RECHAZADO", resultado.getEstadoEntrega());
        assertEquals("Cliente no disponible", resultado.getObservacion());
    }

    @Test
    void actualizarDebeLanzarExcepcionCuandoNoExiste() {
        when(verificacionRepository.findById(5)).thenReturn(Optional.empty());

        Excepcion exception = assertThrows(
                Excepcion.class,
                () -> verificacionService.actualizar(5, crearVerificacionValida()));

        assertEquals("Verificación de despacho no encontrada con ID: 5", exception.getMessage());
    }

    @Test
    void eliminarDebeEliminarVerificacionExistente() {
        Verificacion existente = crearVerificacionValida();
        when(verificacionRepository.findById(1)).thenReturn(Optional.of(existente));

        verificacionService.eliminar(1);

        verify(verificacionRepository).delete(existente);
    }

    @Test
    void eliminarDebeLanzarExcepcionCuandoNoExiste() {
        when(verificacionRepository.findById(7)).thenReturn(Optional.empty());

        Excepcion exception = assertThrows(Excepcion.class, () -> verificacionService.eliminar(7));

        assertEquals("Verificación de despacho no encontrada con ID: 7", exception.getMessage());
    }

    @Test
    void listarDTODebeMapearCamposResumidos() {
        Verificacion verificacion = crearVerificacionValida();
        verificacion.setIdVerificacion(3);
        when(verificacionRepository.findAll()).thenReturn(List.of(verificacion));

        var resultado = verificacionService.listarDTO();

        assertEquals(1, resultado.size());
        assertEquals(3, resultado.getFirst().getIdVerificacion());
        assertEquals("ENTREGADO", resultado.getFirst().getEstadoEntrega());
    }

    @Test
    void obtenerDetalleDTODebeRetornarTodosLosCampos() {
        Verificacion verificacion = crearVerificacionValida();
        verificacion.setIdVerificacion(2);
        when(verificacionRepository.findById(2)).thenReturn(Optional.of(verificacion));

        VerificacionDetalladoDTO resultado = verificacionService.obtenerDetalleDTO(2);

        assertEquals(2, resultado.getIdVerificacion());
        assertEquals("Entrega confirmada", resultado.getObservacion());
    }

    private Verificacion crearVerificacionValida() {
        Verificacion verificacion = new Verificacion();
        verificacion.setIdEnvio(1);
        verificacion.setFechaEntrega("2026-06-20");
        verificacion.setEstadoEntrega("ENTREGADO");
        verificacion.setObservacion("Entrega confirmada");
        return verificacion;
    }
}
