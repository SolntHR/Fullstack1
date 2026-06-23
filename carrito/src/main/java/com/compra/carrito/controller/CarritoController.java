package com.compra.carrito.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.compra.carrito.dto.ValidacionItemResenaDTO;
import com.compra.carrito.model.Carrito;
import com.compra.carrito.model.Pago;
import com.compra.carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito")
@Validated
@Tag(name = "1. Carrito", description = "Operaciones relacionadas con la gestión del carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    @Operation(
            summary = "Listar carritos",
            description = "Obtiene la lista completa de carritos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")})
    @GetMapping("/listar")
    public ResponseEntity<List<Carrito>> listar() {
        return ResponseEntity.ok(carritoService.listar());
    }

    @Operation(
            summary = "Buscar carrito por ID",
            description = "Obtiene un carrito específico a partir de su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Carrito> buscar(@PathVariable Integer id){
        Carrito carrito = carritoService.buscar(id);
        return ResponseEntity.ok(carrito);
    }

    @Operation(
            summary = "Guardar carrito",
            description = "Crea un nuevo carrito en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("/guardar-carrito")
    public ResponseEntity<Carrito> guardar(@Valid @RequestBody Carrito carrito){
        Carrito carritoGuardado = carritoService.guardar(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoGuardado);
    }

    @Operation(
            summary = "Eliminar carrito",
            description = "Elimina un carrito existente por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Aprobar pago de carrito",
            description = "Aprueba el pago asociado a un carrito según el método de pago indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago aprobado correctamente"),
            @ApiResponse(responseCode = "400", description = "Método de pago inválido o solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content)
    })
    @PutMapping("/{idCarrito}/aprobar-pago")
    public ResponseEntity<Pago> aprobarPago(@PathVariable Integer idCarrito,
                                            @RequestParam String metodoPago) {
        Pago pagoAprobado = carritoService.aprobarPago(idCarrito, metodoPago);
        return ResponseEntity.ok(pagoAprobado);
    }

    @Operation(
            summary = "Validar item para reseña",
            description = "Valida si un item de carrito puede ser usado para crear una reseña en otro servicio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validación realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item no encontrado o no válido", content = @Content)
    })
    @GetMapping("/items/{idItemCarrito}/validacion-resena")
    public ResponseEntity<ValidacionItemResenaDTO> validarItemParaResena(
            @PathVariable Integer idItemCarrito,
            @RequestParam Integer idUsuario,
            @RequestParam Integer idProducto) {

        ValidacionItemResenaDTO dto = carritoService.validarItemParaResena(idItemCarrito, idUsuario, idProducto);
        return ResponseEntity.ok(dto);
    }
}
