package com.compra.carrito.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.compra.carrito.model.Carrito;
import com.compra.carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito")
@Validated
@Tag(name = "Carrito", description = "Operaciones para administrar carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar carritos", description = "Obtiene todos los carritos registrados")
    public List<Carrito> listar(){
        return carritoService.listar();
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar carrito", description = "Busca un carrito por su identificador")
    public Carrito buscar(@PathVariable Integer id){
        return carritoService.buscar(id);
    }

    @PostMapping("/guardar-carrito")
    @Operation(summary = "Guardar carrito", description = "Guarda un carrito y genera su pago pendiente")
    public Carrito guardar(@Valid @RequestBody Carrito carrito){
        return carritoService.guardar(carrito);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar carrito", description = "Elimina un carrito por su identificador")
    public void eliminar(@PathVariable Integer id){
        carritoService.eliminar(id);
    }

}
