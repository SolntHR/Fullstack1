package com.compra.carrito.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.cliente.RestTemplateSelector;
import com.compra.carrito.dto.CuponDTO;
import com.compra.carrito.dto.ItemDTO;
import com.compra.carrito.dto.ValidacionItemResenaDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.model.ItemCarrito;
import com.compra.carrito.repository.CarritoRepository;
import com.compra.carrito.repository.PagoRepository;

@Service
public class CarritoService {
    
    private final PagoRepository pagoRepository;
    private final CarritoRepository carritoRepository;
    private final InventarioCliente inventarioCliente;
    private final RestTemplateSelector restTemplateSelector;
    private final String promocionesBaseUrl;

    public CarritoService(
            PagoRepository pagoRepository,
            CarritoRepository carritoRepository,
            InventarioCliente inventarioCliente,
            RestTemplateSelector restTemplateSelector,
            @Value("${services.promociones.base-url:http://promociones}") String promocionesBaseUrl) {
        this.pagoRepository = pagoRepository;
        this.carritoRepository = carritoRepository;
        this.inventarioCliente = inventarioCliente;
        this.restTemplateSelector = restTemplateSelector;
        this.promocionesBaseUrl = promocionesBaseUrl;
    }


    public List<Carrito> listar(){
        return carritoRepository.findAll();
    }

    public Carrito buscar(Integer id){
        return carritoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
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

        if(producto.getStockProducto() < cantidad){
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

        carrito.setEstado("PENDIENTE_PAGO");
        
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

    public void eliminar(Integer id) {
        if (!carritoRepository.existsById(id)) {
            throw new RuntimeException("Carrito no encontrado");
        }
        carritoRepository.deleteById(id);
    }

    public Integer aplicarCupon(Integer total, String codigoPromocional) {
        String url = promocionesBaseUrl + "/promociones/buscar-codigo/" + codigoPromocional;

        try {
            CuponDTO cupon = restTemplateSelector.select(promocionesBaseUrl)
                    .getForObject(url, CuponDTO.class);

            if (cupon != null) {
                LocalDate hoy = LocalDate.now();
                BigDecimal totalBD = BigDecimal.valueOf(total);

                if (hoy.isBefore(cupon.getFechaInicio()) || hoy.isAfter(cupon.getFechaFin())) {
                    throw new RuntimeException("El cupón no está vigente hoy");
                }

                if (totalBD.compareTo(cupon.getMontoMinimo()) < 0) {
                    throw new RuntimeException("El total debe ser al menos $" + cupon.getMontoMinimo());
                }

                BigDecimal descuento = totalBD.multiply(cupon.getDescuento())
                        .divide(BigDecimal.valueOf(100));

                return total - descuento.intValue();
            }
        } catch (Exception e) {
            System.out.println("Error al conectar con promociones: " + e.getMessage());
        }

        return total;
    }

    public Pago aprobarPago(Integer idCarrito, String metodoPago) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Pago pago = pagoRepository.findByIdCarrito(idCarrito)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado para el carrito"));

        if ("APROBADO".equalsIgnoreCase(pago.getEstado())) {
            return pago;
        }

        for (ItemCarrito item : carrito.getItems()) {
            inventarioCliente.descontarStock(item.getIdProducto(), item.getCantidad());
        }

        pago.setEstado("APROBADO");
        pago.setMetodoPago(metodoPago);
        pago.setMonto(carrito.getTotal());
        pago.setFechaCreacion(LocalDateTime.now());
        pagoRepository.save(pago);

        carrito.setEstado("COMPLETADO");
        carritoRepository.save(carrito);

        return pago;
    }

    public ValidacionItemResenaDTO validarItemParaResena(Integer idItemCarrito,
                                                        Integer idUsuario,
                                                        Integer idProducto) {

        List<Carrito> carritos = carritoRepository.findAll();

        for (Carrito carrito : carritos) {
            if (!carrito.getIdUsuario().equals(idUsuario)) {
                continue;
            }

            ItemCarrito itemEncontrado = carrito.getItems()
                    .stream()
                    .filter(item -> item.getId().equals(idItemCarrito))
                    .findFirst()
                    .orElse(null);

            if (itemEncontrado == null) {
                continue;
            }

            boolean itemValido = itemEncontrado.getIdProducto().equals(idProducto);
            boolean carritoPagado = "COMPLETADO".equalsIgnoreCase(carrito.getEstado());

            boolean pagoAprobado = pagoRepository.findByIdCarrito(carrito.getIdCarrito())
                    .map(pago -> "APROBADO".equalsIgnoreCase(pago.getEstado()))
                    .orElse(false);

            return new ValidacionItemResenaDTO(
                    itemEncontrado.getId(),
                    carrito.getIdUsuario(),
                    itemEncontrado.getIdProducto(),
                    carrito.getIdCarrito(),
                    itemValido,
                    carritoPagado,
                    pagoAprobado
            );
        }

        throw new RuntimeException("No se encontró un item de carrito válido para reseñar");
    }

}
