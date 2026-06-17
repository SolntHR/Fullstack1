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

    @GetMapping("/listar-pago")
    public List<Pago> listar(){
        return pagoService.listar();
    }

    @GetMapping("/buscar-pago/{idPago}")
    public Pago buscar(@PathVariable Integer idPago) {
        return pagoService.buscar(idPago);
    }

    @PostMapping("/guardar-pago")
    public Pago guardar(@Valid @RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @DeleteMapping("/eliminar-pago/{idPago}")
    public void eliminar(@PathVariable Integer idPago) {
        pagoService.eliminar(idPago);
    }

    @GetMapping("/pago-simple/{idPago}")
    public PagoSimpleDTO obtenerDTO(@PathVariable Integer idPago) {
    return pagoService.obtenerPagoSimpleDTO(idPago);
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
