package com.compra.carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.cliente.RestTemplateSelector;
import com.compra.carrito.dto.CuponDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    private static final String PROMOCIONES_BASE_URL = "http://promociones";

    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private InventarioCliente inventarioCliente;
    @Mock
    private RestTemplateSelector restTemplateSelector;
    @Mock
    private RestTemplate restTemplate;

    private CarritoService carritoService;

    @BeforeEach
    void setUp() {
        carritoService = new CarritoService(
                pagoRepository,
                carritoRepository,
                inventarioCliente,
                restTemplateSelector,
                PROMOCIONES_BASE_URL);
    }

    @Test
    void guardarDebeCompletarCarritoYCrearPagoPendiente() {
        Carrito carrito = new Carrito();
        carrito.setIdUsuario(25);
        carrito.setEstado("ACTIVO");
        carrito.setTotal(0);

        ItemCarrito item = new ItemCarrito();
        item.setIdProducto(10);
        item.setNombreProducto("Teclado mecanico");
        item.setCantidad(2);
        item.setPrecio(15000);
        carrito.setItems(List.of(item));

        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> {
            Carrito carritoGuardado = invocation.getArgument(0);
            carritoGuardado.setIdCarrito(99);
            return carritoGuardado;
        });
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Carrito resultado = carritoService.guardar(carrito);

        assertEquals(30000, resultado.getTotal());
        assertEquals(0, resultado.getDescuentoAplicado());
        assertEquals("COMPLETADO", resultado.getEstado());
        assertSame(resultado, resultado.getItems().getFirst().getCarrito());
        verify(carritoRepository).save(carrito);
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void aplicarCuponDebeRetornarTotalConDescuentoCuandoElCuponEsValido() {
        CuponDTO cupon = new CuponDTO();
        cupon.setFechaInicio(LocalDate.now().minusDays(1));
        cupon.setFechaFin(LocalDate.now().plusDays(1));
        cupon.setDescuento(java.math.BigDecimal.TEN);
        cupon.setMontoMinimo(java.math.BigDecimal.valueOf(100));

        when(restTemplateSelector.select(PROMOCIONES_BASE_URL)).thenReturn(restTemplate);
        when(restTemplate.getForObject(
                eq(PROMOCIONES_BASE_URL + "/promociones/buscar-codigo/CUPON10"),
                eq(CuponDTO.class)))
                .thenReturn(cupon);

        Integer totalConDescuento = carritoService.aplicarCupon(200, "CUPON10");

        assertEquals(180, totalConDescuento);
    }

    @Test
    void buscarDebeLanzarExcepcionCuandoElCarritoNoExiste() {
        when(carritoRepository.findById(404)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> carritoService.buscar(404));

        assertEquals("Carrito no encontrado", exception.getMessage());
    }
}
