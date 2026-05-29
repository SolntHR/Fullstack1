package com.compra.carrito.controller;

import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.service.CarritoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(name ="/carrito")
@Validated
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    @GetMapping
    public List<Carrito> listar(){
        return carritoService.listar();
    }

    @GetMapping("/buscar/{id}")
    public Carrito buscar(@PathVariable Integer id){
        return carritoService.buscar(id);
    }

    @PostMapping
    public Carrito guardar(@Valid @RequestBody Carrito carrito){
        return carritoService.guardar(carrito);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id){
        carritoService.eliminar(id);
    }
}
