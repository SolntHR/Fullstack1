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
                carrito1.setDescuentoAplicado(62247);
                carrito1.setTotal(352733);
                carrito1.setEstado("COMPLETADO");

                List<ItemCarrito> items1 = new ArrayList<>();

                ItemCarrito item1 = new ItemCarrito();
                item1.setIdProducto(1);
                item1.setNombreProducto("PS5");
                item1.setCantidad(1);
                item1.setPrecio(399990);
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

            if (carritoRepository.findByIdUsuario(3).isEmpty()) {
                Carrito carrito3 = new Carrito();
                carrito3.setIdUsuario(3);
                carrito3.setCodigoCupon("GAMER10");
                carrito3.setDescuentoAplicado(42997);
                carrito3.setTotal(386973);
                carrito3.setEstado("COMPLETADO");

                List<ItemCarrito> items3 = new ArrayList<>();

                ItemCarrito item5 = new ItemCarrito();
                item5.setIdProducto(1);
                item5.setNombreProducto("PS5");
                item5.setCantidad(1);
                item5.setPrecio(399990);
                item5.setCarrito(carrito3);

                ItemCarrito item6 = new ItemCarrito();
                item6.setIdProducto(2);
                item6.setNombreProducto("Mouse Inalambrico");
                item6.setCantidad(2);
                item6.setPrecio(14990);
                item6.setCarrito(carrito3);

                items3.add(item5);
                items3.add(item6);
                carrito3.setItems(items3);

                Carrito carritoGuardado3 = carritoRepository.save(carrito3);

                if (pagoRepository.findByIdCarrito(carritoGuardado3.getIdCarrito()).isEmpty()) {
                    Pago pago3 = new Pago();
                    pago3.setIdCarrito(carritoGuardado3.getIdCarrito());
                    pago3.setMetodoPago("TARJETA");
                    pago3.setMonto(carritoGuardado3.getTotal());
                    pago3.setEstado("APROBADO");
                    pago3.setFechaCreacion(LocalDateTime.parse("25/05/2026 17:45", formatter));
                    pagoRepository.save(pago3);
                }
            }

            if (carritoRepository.findByIdUsuario(4).isEmpty()) {
                Carrito carrito4 = new Carrito();
                carrito4.setIdUsuario(4);
                carrito4.setCodigoCupon(null);
                carrito4.setDescuentoAplicado(0);
                carrito4.setTotal(175980);
                carrito4.setEstado("COMPLETADO");

                List<ItemCarrito> items4 = new ArrayList<>();

                ItemCarrito item7 = new ItemCarrito();
                item7.setIdProducto(3);
                item7.setNombreProducto("Teclado Mecanico");
                item7.setCantidad(1);
                item7.setPrecio(45990);
                item7.setCarrito(carrito4);

                ItemCarrito item8 = new ItemCarrito();
                item8.setIdProducto(4);
                item8.setNombreProducto("Monitor 24 Pulgadas");
                item8.setCantidad(1);
                item8.setPrecio(129990);
                item8.setCarrito(carrito4);

                items4.add(item7);
                items4.add(item8);
                carrito4.setItems(items4);

                Carrito carritoGuardado4 = carritoRepository.save(carrito4);

                if (pagoRepository.findByIdCarrito(carritoGuardado4.getIdCarrito()).isEmpty()) {
                    Pago pago4 = new Pago();
                    pago4.setIdCarrito(carritoGuardado4.getIdCarrito());
                    pago4.setMetodoPago("TRANSFERENCIA");
                    pago4.setMonto(carritoGuardado4.getTotal());
                    pago4.setEstado("APROBADO");
                    pago4.setFechaCreacion(LocalDateTime.parse("26/05/2026 12:20", formatter));
                    pagoRepository.save(pago4);
                }
            }

            if (carritoRepository.findByIdUsuario(5).isEmpty()) {
                Carrito carrito5 = new Carrito();
                carrito5.setIdUsuario(5);
                carrito5.setCodigoCupon("PERIFERICOS5");
                carrito5.setDescuentoAplicado(3049);
                carrito5.setTotal(57931);
                carrito5.setEstado("COMPLETADO");

                List<ItemCarrito> items5 = new ArrayList<>();

                ItemCarrito item9 = new ItemCarrito();
                item9.setIdProducto(2);
                item9.setNombreProducto("Mouse Inalambrico");
                item9.setCantidad(1);
                item9.setPrecio(14990);
                item9.setCarrito(carrito5);

                ItemCarrito item10 = new ItemCarrito();
                item10.setIdProducto(3);
                item10.setNombreProducto("Teclado Mecanico");
                item10.setCantidad(1);
                item10.setPrecio(45990);
                item10.setCarrito(carrito5);

                items5.add(item9);
                items5.add(item10);
                carrito5.setItems(items5);

                Carrito carritoGuardado5 = carritoRepository.save(carrito5);

                if (pagoRepository.findByIdCarrito(carritoGuardado5.getIdCarrito()).isEmpty()) {
                    Pago pago5 = new Pago();
                    pago5.setIdCarrito(carritoGuardado5.getIdCarrito());
                    pago5.setMetodoPago("TARJETA");
                    pago5.setMonto(carritoGuardado5.getTotal());
                    pago5.setEstado("APROBADO");
                    pago5.setFechaCreacion(LocalDateTime.parse("27/05/2026 20:15", formatter));
                    pagoRepository.save(pago5);
                }
            }
        };
    }
}