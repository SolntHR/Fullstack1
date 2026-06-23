package com.compra.carrito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compra.carrito.dto.PagoSimpleDTO;
import com.compra.carrito.model.Pago;
import com.compra.carrito.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito/pagos")
@Tag(name = "Pagos", description = "Operaciones para gestionar pagos del carrito")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @GetMapping("/listar-pago")
    @Operation(summary = "Listar pagos", description = "Obtiene todos los pagos registrados")
    public List<Pago> listar(){
        return pagoService.listar();
    }

    @GetMapping("/buscar-pago/{idPago}")
    @Operation(summary = "Buscar pago", description = "Busca un pago por su identificador")
    public Pago buscar(@PathVariable Integer idPago) {
        return pagoService.buscar(idPago);
    }

    @PostMapping("/guardar-pago")
    @Operation(summary = "Guardar pago", description = "Aprueba el pago de un carrito y descuenta stock")
    public Pago guardar(@Valid @RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @DeleteMapping("/eliminar-pago/{idPago}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago por su identificador")
    public void eliminar(@PathVariable Integer idPago) {
        pagoService.eliminar(idPago);
    }

    @GetMapping("/pago-simple/{idPago}")
    @Operation(summary = "Obtener pago simple", description = "Devuelve la vista resumida de un pago")
    public PagoSimpleDTO obtenerDTO(@PathVariable Integer idPago) {
    return pagoService.obtenerPagoSimpleDTO(idPago);
    }

    @GetMapping("/rango-fechas")
    @Operation(summary = "Buscar pagos por rango", description = "Lista pagos entre dos fechas ISO-8601")
    public List<PagoSimpleDTO> listarPorRango(@RequestParam String inicio, @RequestParam String fin) {
        return pagoService.buscarPorRangoSimple(inicio, fin);
    }

    @GetMapping("/validar-compra/{idPago}/{idUsuario}/{idProducto}")
    @Operation(summary = "Validar compra", description = "Valida si un usuario compro un producto en un pago aprobado")
    public ResponseEntity<Boolean> validarCompra(
            @PathVariable Integer idPago,
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {

        boolean valida = pagoService.validarCompra(idPago, idUsuario, idProducto);
        return ResponseEntity.ok(valida);
    }
}
