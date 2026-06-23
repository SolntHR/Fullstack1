package com.envio.despacho.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.envio.despacho.dto.VerificacionDetalladoDTO;
import com.envio.despacho.dto.VerificacionListadoDTO;
import com.envio.despacho.dto.VerificacionSimpleDTO;
import com.envio.despacho.model.Verificacion;
import com.envio.despacho.service.VerificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/verificaciones")
@RequiredArgsConstructor
public class VerificacionController {

    private final VerificacionService service;

    @Operation(
            summary = "Listar todos los despachos verificados",
            description = "Obtiene la lista completa de verificaciones de despacho",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de verificaciones obtenida correctamente")
    })
    @GetMapping("/listar-verif")
    public ResponseEntity<List<Verificacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(
            summary = "Buscar verificación por ID",
            description = "Obtiene una verificación específica a partir de su identificador",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la verificación")
    })
    @GetMapping("/buscar-verif/{id}")
    public ResponseEntity<Verificacion> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @Operation(
            summary = "Guardar verificación",
            description = "Crea una nueva verificación de despacho",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Verificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping("/guardar-verif")
    public ResponseEntity<Verificacion> guardar(@Valid @RequestBody Verificacion verificacion) {
        Verificacion nuevaVerificacion = service.guardar(verificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVerificacion);
    }

    @Operation(
            summary = "Actualizar verificación",
            description = "Actualiza los datos de una verificación existente",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "No se encontró la verificación")
    })
    @PutMapping("/actualizar-verif/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Verificacion verificacion) {
        Verificacion actualizada = service.actualizar(id, verificacion);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Verificación actualizada exitosamente");
        response.put("verificacion", actualizada);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar verificación",
            description = "Elimina una verificación existente a partir de su ID",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la verificación")
    })
    @DeleteMapping("/eliminar-verif/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok("Verificación eliminada con éxito");
    }

    @Operation(
            summary = "Obtener listado DTO de verificaciones",
            description = "Obtiene una vista resumida de verificaciones con información relevante",
            tags = {"3. DTOs"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente")
    })
    @GetMapping("/listadoDTO")
    public ResponseEntity<List<VerificacionListadoDTO>> obtenerListadoDTO() {
        return ResponseEntity.ok(service.listarDTO());
    }

    @Operation(
            summary = "Obtener listado simple DTO de verificaciones",
            description = "Obtiene una vista simple de verificaciones",
            tags = {"3. DTOs"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado simple obtenido correctamente")
    })
    @GetMapping("/simpleDTO")
    public ResponseEntity<List<VerificacionSimpleDTO>> obtenerSimpleDTO() {
        return ResponseEntity.ok(service.listarSimpleDTO());
    }

    @Operation(
            summary = "Obtener detalle DTO de verificación",
            description = "Obtiene el detalle completo de una verificación en formato DTO",
            tags = {"3. DTOs"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de la verificación obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la verificación")
    })
    @GetMapping("/{id}/detalleDTO")
    public ResponseEntity<VerificacionDetalladoDTO> obtenerDetalleDTO(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerDetalleDTO(id));
    }
}
