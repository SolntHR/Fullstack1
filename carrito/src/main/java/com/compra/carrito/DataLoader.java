package com.compra.carrito;

import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initCompra(CarritoRepository carritoRepository, PagoRepository pagoRepository) {
        return args -> {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (carritoRepository.findByIdUsuario(1).isEmpty()) {
                Carrito carrito1 = new Carrito();
                carrito1.setIdUsuario(1);
                carrito1.setCodigoCupon("GAMER15");
                carrito1.setDescuentoAplicado(8250);
                carrito1.setTotal(46740);
                carrito1.setEstado("COMPLETADO");

                List<ItemCarrito> items1 = new ArrayList<>();

                ItemCarrito item1 = new ItemCarrito();
                item1.setIdProducto(1);
                item1.setNombreProducto("Audifonos Gamer");
                item1.setCantidad(1);
                item1.setPrecio(39990);
                item1.setCarrito(carrito1);

                ItemCarrito item2 = new ItemCarrito();
                item2.setIdProducto(2);
                item2.setNombreProducto("Mouse Inalambrico");
                item2.setCantidad(1);
                item2.setPrecio(14990);
                item2.setCarrito(carrito1);

                items1.add(item1);
                items1.add(item2);
                carrito1.setItems(items1);

                Carrito carritoGuardado1 = carritoRepository.save(carrito1);

                if (pagoRepository.findByIdCarrito(carritoGuardado1.getIdCarrito()).isEmpty()) {
                    Pago pago1 = new Pago();
                    pago1.setIdCarrito(carritoGuardado1.getIdCarrito());
                    pago1.setMetodoPago("TARJETA");
                    pago1.setMonto(carritoGuardado1.getTotal());
                    pago1.setEstado("APROBADO");
                    pago1.setFechaCreacion(LocalDateTime.parse("20/05/2026 16:30", formatter));
                    pagoRepository.save(pago1);
                }
            }

            if (carritoRepository.findByIdUsuario(2).isEmpty()) {
                Carrito carrito2 = new Carrito();
                carrito2.setIdUsuario(2);
                carrito2.setCodigoCupon(null);
                carrito2.setDescuentoAplicado(0);
                carrito2.setTotal(175980);
                carrito2.setEstado("COMPLETADO");

                List<ItemCarrito> items2 = new ArrayList<>();

                ItemCarrito item3 = new ItemCarrito();
                item3.setIdProducto(3);
                item3.setNombreProducto("Teclado Mecanico");
                item3.setCantidad(1);
                item3.setPrecio(45990);
                item3.setCarrito(carrito2);

                ItemCarrito item4 = new ItemCarrito();
                item4.setIdProducto(4);
                item4.setNombreProducto("Monitor 24 Pulgadas");
                item4.setCantidad(1);
                item4.setPrecio(129990);
                item4.setCarrito(carrito2);

                items2.add(item3);
                items2.add(item4);
                carrito2.setItems(items2);

                Carrito carritoGuardado2 = carritoRepository.save(carrito2);

                if (pagoRepository.findByIdCarrito(carritoGuardado2.getIdCarrito()).isEmpty()) {
                    Pago pago2 = new Pago();
                    pago2.setIdCarrito(carritoGuardado2.getIdCarrito());
                    pago2.setMetodoPago("TRANSFERENCIA");
                    pago2.setMonto(carritoGuardado2.getTotal());
                    pago2.setEstado("APROBADO");
                    pago2.setFechaCreacion(LocalDateTime.parse("22/05/2026 19:10", formatter));
                    pagoRepository.save(pago2);
                }
            }
        };
    }
}
