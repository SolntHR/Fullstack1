package com.compra.carrito.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.dto.CuponDTO;
import com.compra.carrito.dto.ItemDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;

@Service
public class CarritoService {

    @Autowired
    private final CarritoRepository carritoRepository;
    private InventarioCliente inventarioCliente;
    @Autowired
    private PagoRepository pagoRepository;


    public CarritoService(CarritoRepository carritoRepository){
        this.carritoRepository = carritoRepository;
    }

    public List<Carrito> listar(){
        return carritoRepository.findAll();
    }

    public Carrito agregarProducto(Integer idProducto,
                                  Integer cantidad,
                                  Integer idUsuario) {

        ItemDTO producto =
                inventarioCliente.obtenerProducto(idProducto);

        if(producto == null){
            throw new RuntimeException(
                    "Producto no encontrado");
        }

        if(producto.getStock() < cantidad){
            throw new RuntimeException(
                    "Stock insuficiente");
        }

        Carrito carrito = new Carrito();

        carrito.setIdUsuario(idUsuario);

        return carritoRepository.save(carrito);
    }

    public Carrito guardar(Carrito carrito) {
        Integer totalBase = carrito.getItems()
                .stream()
                .mapToInt(item -> item.getSubTotal())
                .sum();
        
        if (carrito.getCodigoCupon() != null && !carrito.getCodigoCupon().isEmpty()) {
            Integer totalFinal = aplicarCupon(totalBase, carrito.getCodigoCupon());
            carrito.setDescuentoAplicado(totalBase - totalFinal);
            carrito.setTotal(totalFinal);
        } else {
            carrito.setTotal(totalBase);
            carrito.setDescuentoAplicado(0);
        }

        carrito.setEstado("COMPLETADO");
        
        carrito.getItems().forEach(item -> item.setCarrito(carrito));
        Carrito carritoGuardado = carritoRepository.save(carrito);

        Pago nuevoPago = new Pago();
        nuevoPago.setIdCarrito(carritoGuardado.getIdCarrito());
        nuevoPago.setMonto(carritoGuardado.getTotal());
        nuevoPago.setEstado("PENDIENTE");
        nuevoPago.setFechaCreacion(LocalDateTime.now());
        nuevoPago.setMetodoPago("PENDIENTE");
        
        pagoRepository.save(nuevoPago);
        return carritoGuardado;
    }

    public void eliminar(Integer id){
        carritoRepository.deleteById(id.longValue());
    }

    public Carrito buscar(Integer id){
        return carritoRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public Integer aplicarCupon(Integer total, String codigoPromocional) {

        RestTemplate restTemplate = new RestTemplate();
        
        String url = "http://localhost:8088/promociones/buscar-codigo/" + codigoPromocional;

        try {
            CuponDTO cupon = restTemplate.getForObject(url, CuponDTO.class);

            if (cupon != null) {
                LocalDate hoy = LocalDate.now();
                BigDecimal totalBD = BigDecimal.valueOf(total);

                if (hoy.isBefore(cupon.getFechaInicio()) || hoy.isAfter(cupon.getFechaFin())) {
                    throw new RuntimeException("El cupón no está vigente hoy");
                }
                if (totalBD.compareTo(cupon.getMontoMinimo()) < 0) {
                    throw new RuntimeException("El total debe ser al menos $" + cupon.getMontoMinimo());
                }

                BigDecimal descuento = totalBD.multiply(cupon.getDescuento()).divide(BigDecimal.valueOf(100));

                return total - descuento.intValue();
            }
        } catch (Exception e) {
            System.out.println("Error al conectar con promociones: " + e.getMessage());
        }

        return total;
    }

}
