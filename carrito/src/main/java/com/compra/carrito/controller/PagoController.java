package com.compra.carrito.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compra.carrito.dto.PagoSimpleDTO;
import com.compra.carrito.model.Pago;
import com.compra.carrito.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito/pagos")
@Tag(name = "2. Pagos", description = "Operaciones relacionadas con los pagos del carrito")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene la lista completa de pagos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pagos obtenido correctamente")
    })
    @GetMapping("/listar-pago")
    public List<Pago> listar(){
        return pagoService.listar();
    }

    @Operation(
            summary = "Buscar pago por ID",
            description = "Obtiene un pago específico a partir de su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content)
    })
    @GetMapping("/buscar-pago/{idPago}")
    public Pago buscar(@PathVariable Integer idPago) {
        return pagoService.buscar(idPago);
    }

    @Operation(
            summary = "Guardar pago",
            description = "Registra un nuevo pago en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago guardado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("/guardar-pago")
    public Pago guardar(@Valid @RequestBody Pago pago) {
        return pagoService.guardar(pago);
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago existente a partir de su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar-pago/{idPago}")
    public void eliminar(@PathVariable Integer idPago) {
        pagoService.eliminar(idPago);
    }

    @Operation(
            summary = "Obtener pago simple DTO",
            description = "Obtiene la representación resumida de un pago"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DTO obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content)
    })
    @GetMapping("/pago-simple/{idPago}")
    public PagoSimpleDTO obtenerDTO(@PathVariable Integer idPago) {
    return pagoService.obtenerPagoSimpleDTO(idPago);
    }

    @Operation(
            summary = "Buscar pagos por rango de fechas",
            description = "Obtiene pagos resumidos filtrados entre una fecha de inicio y una fecha de fin"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos encontrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Rango de fechas inválido", content = @Content)
    })
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<PagoSimpleDTO>> listarPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(pagoService.buscarPorRangoSimple(inicio, fin));
    }

}
