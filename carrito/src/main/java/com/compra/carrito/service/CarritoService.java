package com.compra.carrito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compra.carrito.cliente.InventarioCliente;
import com.compra.carrito.dto.CuponDTO;
import com.compra.carrito.dto.ItemDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.repository.CarritoRepository;

@Service
public class CarritoService {

    @Autowired
    private final CarritoRepository carritoRepository;
    private Promocion promocion;
    private InventarioCliente inventarioCliente;


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

    public Carrito guardar(Carrito carrito){
        Integer total = carrito.getItems()
            .stream()
            .mapToInt(item -> item.getSubTotal())
            .sum();
        
        carrito.setTotal(total);
        carrito.getItems().forEach(item -> item.setCarrito(carrito));

        return carritoRepository.save(carrito);
            
    }

    public void eliminar(Integer id){
        carritoRepository.deleteById(id.longValue());
    }

    public Carrito buscar(Integer id){
        return carritoRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public Integer aplicarCupon(
            Integer total,
            String codigoPromocional) {

        CuponDTO cupon =
                promocion.obtenerCupon(codigoPromocional);

        if(cupon != null &&
           cupon.getActivo()) {

            Integer descuento =
                    total * cupon.getDescuento() / 100;

            return total - descuento;
        }

        return total;
    }
}
