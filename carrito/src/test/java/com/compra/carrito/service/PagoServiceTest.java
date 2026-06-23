package com.compra.carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.dto.PagoSimpleDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private InventarioCliente inventarioCliente;
    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private CarritoService carritoService;

    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        pagoService = new PagoService(
                pagoRepository,
                carritoRepository,
                inventarioCliente,
                carritoService);
    }

    @Test
    void guardarDebeAsignarValoresPorDefectoCuandoFaltan() {
        Pago pagoEntrada = new Pago();
        pagoEntrada.setIdCarrito(10);
        pagoEntrada.setMetodoPago("TARJETA");
        pagoEntrada.setMonto(50000);
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago resultado = pagoService.guardar(pagoEntrada);

        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals("TARJETA", resultado.getMetodoPago());
        assertEquals(50000, resultado.getMonto());
        assertNotNull(resultado.getFechaCreacion());
        verify(pagoRepository).save(pagoEntrada);
    }

    @Test
    void procesarPagoDebeAprobarPagoYDescontarStock() {
        Carrito carrito = crearCarrito();
        Pago pago = new Pago();
        pago.setIdPago(7);
        pago.setIdCarrito(10);
        pago.setMetodoPago("TARJETA");
        pago.setMonto(50000);
        pago.setEstado("PENDIENTE");

        when(carritoService.buscar(10)).thenReturn(carrito);
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago resultado = pagoService.procesarPago(pago);

        assertEquals("APROBADO", resultado.getEstado());
        assertNotNull(resultado.getFechaCreacion());
        verify(inventarioCliente, times(2)).descontarStock(any(Integer.class), any(Integer.class));
        verify(pagoRepository).save(pago);
    }

    @Test
    void obtenerPagoSimpleDTODebeMapearLosCamposDelPago() {
        Pago pago = new Pago();
        pago.setIdPago(7);
        pago.setIdCarrito(10);
        pago.setMetodoPago("TARJETA");
        pago.setMonto(50000);
        pago.setEstado("APROBADO");
        pago.setFechaCreacion(LocalDateTime.now());

        when(pagoRepository.findById(7)).thenReturn(Optional.of(pago));

        PagoSimpleDTO resultado = pagoService.obtenerPagoSimpleDTO(7);

        assertNotNull(resultado);
        assertEquals(7, resultado.getIdPago());
        assertEquals(50000, resultado.getMonto());
        assertEquals("APROBADO", resultado.getEstado());
        assertEquals("TARJETA", resultado.getMetodoPago());
    }

    private Carrito crearCarrito() {
        ItemCarrito itemUno = new ItemCarrito();
        itemUno.setIdProducto(1);
        itemUno.setNombreProducto("Mouse");
        itemUno.setCantidad(1);
        itemUno.setPrecio(20000);

        ItemCarrito itemDos = new ItemCarrito();
        itemDos.setIdProducto(2);
        itemDos.setNombreProducto("Monitor");
        itemDos.setCantidad(1);
        itemDos.setPrecio(30000);

        Carrito carrito = new Carrito();
        carrito.setIdCarrito(10);
        carrito.setIdUsuario(99);
        carrito.setEstado("ACTIVO");
        carrito.setTotal(50000);
        carrito.setItems(List.of(itemUno, itemDos));
        return carrito;
    }
}
