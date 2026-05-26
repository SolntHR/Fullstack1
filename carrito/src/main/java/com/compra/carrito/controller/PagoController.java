package com.compra.carrito.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.compra.carrito.model.Pago;
import com.compra.carrito.service.PagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<Pago> listar(){
        return pagoService.listar();
    }

    @GetMapping("/{id}")
    public Pago buscar(@PathVariable Integer id) {
        return pagoService.buscar(id);
    }

    @PostMapping
    public Pago guardar(@Valid @RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
    }

}
