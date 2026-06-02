package com.compra.carrito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compra.carrito.dto.PagoSimpleDTO;
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

    @PostMapping("/guardar-pago")
    public Pago guardar(@Valid @RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
    }

    @GetMapping("/pago-simple-dto/{id}")
    public PagoSimpleDTO obtenerDTO(@PathVariable Integer id) {
    return pagoService.obtenerPagoSimpleDTO(id);
    }

    @GetMapping("/rango-fechas")
    public List<PagoSimpleDTO> listarPorRango(@RequestParam String inicio, @RequestParam String fin) {
        return pagoService.buscarPorRangoSimple(inicio, fin);
    }

    @GetMapping("/validar-compra/{idPago}/{idUsuario}/{idProducto}")
    public ResponseEntity<Boolean> validarCompra(
            @PathVariable Integer idPago,
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {

        boolean valida = pagoService.validarCompra(idPago, idUsuario, idProducto);
        return ResponseEntity.ok(valida);
    }
}
