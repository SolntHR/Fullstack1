package com.resena.resena.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.resena.resena.dto.ResenaDetalleDTO;
import com.resena.resena.dto.ResenaListadoDTO;
import com.resena.resena.dto.ResenaSimpleDTO;
import com.resena.resena.model.Resena;
import com.resena.resena.service.ResenaService;

@ExtendWith(MockitoExtension.class)
class ResenaControllerTest {

    @Mock
    private ResenaService resenaService;

    @InjectMocks
    private ResenaController resenaController;

    @Test
    void listarResenasDebeRetornarListaConStatusOk() {
        List<Resena> resenas = List.of(crearResena(1, 10, 20, 30, 5, "Excelente"));
        when(resenaService.listarResenas()).thenReturn(resenas);

        ResponseEntity<List<Resena>> response = resenaController.listarResenas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resenas, response.getBody());
    }

    @Test
    void buscarPorIdResenaDebeRetornarResenaCuandoExiste() {
        Resena resena = crearResena(1, 10, 20, 30, 5, "Excelente");
        when(resenaService.buscarPorIdResena(1)).thenReturn(Optional.of(resena));

        ResponseEntity<Resena> response = resenaController.buscarPorIdResena(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resena, response.getBody());
    }

    @Test
    void buscarPorIdResenaDebeRetornarNotFoundCuandoNoExiste() {
        when(resenaService.buscarPorIdResena(99)).thenReturn(Optional.empty());

        ResponseEntity<Resena> response = resenaController.buscarPorIdResena(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void buscarPorIdUsuarioDebeRetornarListaConStatusOk() {
        List<Resena> resenas = List.of(crearResena(1, 10, 20, 30, 5, "Excelente"));
        when(resenaService.buscarPorIdUsuario(10)).thenReturn(resenas);

        ResponseEntity<List<Resena>> response = resenaController.buscarPorIdUsuario(10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resenas, response.getBody());
    }

    @Test
    void buscarPorIdProductoDebeRetornarListaConStatusOk() {
        List<Resena> resenas = List.of(crearResena(1, 10, 20, 30, 5, "Excelente"));
        when(resenaService.buscarPorIdProducto(20)).thenReturn(resenas);

        ResponseEntity<List<Resena>> response = resenaController.buscarPorIdProducto(20);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resenas, response.getBody());
    }

    @Test
    void agregarResenaDebeRetornarCreated() {
        Resena resena = crearResena(null, 10, 20, 30, 5, "Excelente");
        Resena guardada = crearResena(1, 10, 20, 30, 5, "Excelente");
        when(resenaService.agregarResena(resena)).thenReturn(guardada);

        ResponseEntity<Resena> response = resenaController.agregarResena(resena);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(guardada, response.getBody());
    }

    @Test
    void actualizarResenaDebeRetornarMensajeOkCuandoExiste() {
        Resena actualizada = crearResena(null, 10, 20, 30, 4, "Actualizada");
        when(resenaService.resenaUpdate(1, actualizada))
                .thenReturn(Optional.of(crearResena(1, 10, 20, 30, 4, "Actualizada")));

        ResponseEntity<String> response = resenaController.actualizarResena(1, actualizada);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reseña actualizada exitosamente", response.getBody());
    }

    @Test
    void actualizarResenaDebeRetornarBadRequestCuandoNoExiste() {
        Resena actualizada = crearResena(null, 10, 20, 30, 4, "Actualizada");
        when(resenaService.resenaUpdate(99, actualizada)).thenReturn(Optional.empty());

        ResponseEntity<String> response = resenaController.actualizarResena(99, actualizada);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La reseña que indica no ha sido encontrada", response.getBody());
    }

    @Test
    void eliminarResenaDebeRetornarMensajeOkCuandoExiste() {
        Resena resena = crearResena(1, 10, 20, 30, 5, "Excelente");
        when(resenaService.buscarPorIdResena(1)).thenReturn(Optional.of(resena));

        ResponseEntity<String> response = resenaController.eliminarResena(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reseña eliminada con éxito", response.getBody());
        verify(resenaService).eliminarResena(1);
    }

    @Test
    void eliminarResenaDebeRetornarBadRequestCuandoNoExiste() {
        when(resenaService.buscarPorIdResena(99)).thenReturn(Optional.empty());

        ResponseEntity<String> response = resenaController.eliminarResena(99);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La reseña que indica no ha sido encontrada", response.getBody());
    }

    @Test
    void obtenerListadoDebeRetornarListaDTOConStatusOk() {
        ResenaListadoDTO dto = new ResenaListadoDTO();
        dto.setIdResena(1);
        dto.setComentario("Excelente");
        List<ResenaListadoDTO> listado = List.of(dto);
        when(resenaService.listarDTO()).thenReturn(listado);

        ResponseEntity<List<ResenaListadoDTO>> response = resenaController.obtenerListado();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listado, response.getBody());
    }

    @Test
    void obtenerSimpleDebeRetornarListaSimpleDTOConStatusOk() {
        ResenaSimpleDTO dto = new ResenaSimpleDTO();
        dto.setIdResena(1);
        dto.setEstrellas(5);
        dto.setComentario("Excelente");
        List<ResenaSimpleDTO> listado = List.of(dto);
        when(resenaService.listarSimpleDTO()).thenReturn(listado);

        ResponseEntity<List<ResenaSimpleDTO>> response = resenaController.obtenerSimple();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listado, response.getBody());
    }

    @Test
    void obtenerDetalleDebeRetornarDetalleCuandoExiste() {
        ResenaDetalleDTO dto = new ResenaDetalleDTO();
        dto.setIdResena(1);
        dto.setIdUsuario(10);
        dto.setIdProducto(20);
        dto.setIdItemCarrito(30);
        dto.setEstrellas(5);
        dto.setComentario("Excelente");
        when(resenaService.obtenerDetalleDTO(1)).thenReturn(dto);

        ResponseEntity<ResenaDetalleDTO> response = resenaController.obtenerDetalle(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void obtenerDetalleDebeRetornarNotFoundCuandoNoExiste() {
        when(resenaService.obtenerDetalleDTO(99)).thenReturn(null);

        ResponseEntity<ResenaDetalleDTO> response = resenaController.obtenerDetalle(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    private Resena crearResena(
            Integer idResena,
            Integer idUsuario,
            Integer idProducto,
            Integer idItemCarrito,
            Integer estrellas,
            String comentario) {
        Resena resena = new Resena();
        resena.setIdResena(idResena);
        resena.setIdUsuario(idUsuario);
        resena.setIdProducto(idProducto);
        resena.setIdItemCarrito(idItemCarrito);
        resena.setEstrellas(estrellas);
        resena.setComentario(comentario);
        resena.setFechaCreacion(LocalDateTime.of(2026, 6, 1, 10, 0));
        return resena;
    }
}
