package com.envio.despacho.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.envio.despacho.cliente.ClientePago;
import com.envio.despacho.dto.CarritoDTO;
import com.envio.despacho.dto.EnvioDetalladoDTO;
import com.envio.despacho.exception.Excepcion;
import com.envio.despacho.model.Envio;
import com.envio.despacho.repository.EnvioRepository;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;
    @Mock
    private ClientePago clientePago;

    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        envioService = new EnvioService(envioRepository, clientePago);
    }

    @Test
    void listarDebeRetornarTodosLosEnvios() {
        Envio envio = crearEnvioValido();
        when(envioRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> resultado = envioService.listar();

        assertEquals(1, resultado.size());
        assertEquals(envio.getDireccion(), resultado.getFirst().getDireccion());
    }

    @Test
    void buscarDebeRetornarEnvioCuandoExiste() {
        Envio envio = crearEnvioValido();
        when(envioRepository.findById(1)).thenReturn(Optional.of(envio));

        Optional<Envio> resultado = envioService.buscar(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getIdCarrito());
    }

    @Test
    void guardarDebePersistirEnvioCuandoElCarritoExiste() {
        Envio envio = crearEnvioValido();
        CarritoDTO carrito = new CarritoDTO(1, 10, 50000);

        when(clientePago.obtenerCarrito(1)).thenReturn(carrito);
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.guardar(envio);

        assertEquals("PENDIENTE", resultado.getEstado());
        verify(envioRepository).save(envio);
    }

    @Test
    void guardarDebeLanzarExcepcionCuandoElCarritoNoExiste() {
        Envio envio = crearEnvioValido();
        when(clientePago.obtenerCarrito(1)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> envioService.guardar(envio));

        assertEquals("Carrito no encontrado", exception.getMessage());
    }

    @Test
    void actualizarDebeGuardarEnvioConElIdIndicado() {
        Envio envio = crearEnvioValido();
        when(envioRepository.save(any(Envio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Envio resultado = envioService.actualizar(5, envio);

        assertEquals(5, resultado.getIdEnvio());
        verify(envioRepository).save(envio);
    }

    @Test
    void eliminarDebeInvocarRepositorio() {
        envioService.eliminar(3);

        verify(envioRepository).deleteById(3);
    }

    @Test
    void listarDTODebeMapearCamposResumidos() {
        Envio envio = crearEnvioValido();
        envio.setIdEnvio(10);
        when(envioRepository.findAll()).thenReturn(List.of(envio));

        var resultado = envioService.listarDTO();

        assertEquals(1, resultado.size());
        assertEquals(10, resultado.getFirst().getIdEnvio());
        assertEquals("Av. Libertador 1234", resultado.getFirst().getDireccion());
    }

    @Test
    void obtenerDetalleDTODebeLanzarExcepcionCuandoNoExiste() {
        when(envioRepository.findById(8)).thenReturn(Optional.empty());

        Excepcion exception = assertThrows(Excepcion.class, () -> envioService.obtenerDetalleDTO(8));

        assertEquals("Envío no encontrado con ID: 8", exception.getMessage());
    }

    @Test
    void obtenerDetalleDTODebeRetornarTodosLosCampos() {
        Envio envio = crearEnvioValido();
        envio.setIdEnvio(4);
        when(envioRepository.findById(4)).thenReturn(Optional.of(envio));

        EnvioDetalladoDTO resultado = envioService.obtenerDetalleDTO(4);

        assertEquals(4, resultado.getIdEnvio());
        assertEquals("Metropolitana", resultado.getRegion());
    }

    private Envio crearEnvioValido() {
        Envio envio = new Envio();
        envio.setIdCarrito(1);
        envio.setDireccion("Av. Libertador 1234");
        envio.setComuna("Santiago");
        envio.setRegion("Metropolitana");
        envio.setEstado("PENDIENTE");
        return envio;
    }
}
