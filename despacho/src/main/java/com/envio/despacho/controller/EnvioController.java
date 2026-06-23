package com.envio.despacho.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.envio.despacho.dto.EnvioDetalladoDTO;
import com.envio.despacho.dto.EnvioListadoDTO;
import com.envio.despacho.dto.EnvioSimpleDTO;
import com.envio.despacho.model.Envio;
import com.envio.despacho.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
@Validated
public class EnvioController {

    private final EnvioService envioService;

    @Operation(
            summary = "Listar todos los envíos",
            description = "Obtiene la lista completa de envíos registrados",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida correctamente")
    })
    @GetMapping("/listar-envio")
    public ResponseEntity<List<Envio>> listar() {
        return ResponseEntity.ok(envioService.listar());
    }

    @Operation(
            summary = "Buscar envío por ID",
            description = "Obtiene un envío específico a partir de su identificador",
            tags = {"1. Consultas"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el envío")
    })
    @GetMapping("/buscar-envio/{idEnvio}")
    public ResponseEntity<Envio> buscar(@PathVariable Integer idEnvio) {
        Optional<Envio> envio = envioService.buscar(idEnvio);
        return envio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Guardar envío",
            description = "Crea un nuevo envío validando que el carrito asociado exista",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envío creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PostMapping("/guardar-envio")
    public ResponseEntity<Envio> guardar(@Valid @RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.guardar(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEnvio);
    }

    @Operation(
            summary = "Actualizar envío",
            description = "Actualiza los datos de un envío existente",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "No se encontró el envío")
    })
    @PutMapping("/actualizar-envio/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizar(id, envio);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Envío actualizado exitosamente");
        response.put("envio", actualizado);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar envío",
            description = "Elimina un envío existente a partir de su ID",
            tags = {"2. Gestión"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el envío")
    })
    @DeleteMapping("/eliminar-envio/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        envioService.eliminar(id);
        return ResponseEntity.ok("Envío eliminado con éxito");
    }

    @Operation(
            summary = "Obtener listado DTO de envíos",
            description = "Obtiene una vista resumida de envíos con información relevante",
            tags = {"3. DTOs"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente")
    })
    @GetMapping("/listadoDTO")
    public ResponseEntity<List<EnvioListadoDTO>> obtenerListadoDTO() {
        return ResponseEntity.ok(envioService.listarDTO());
    }

    @Operation(
            summary = "Obtener listado simple DTO de envíos",
            description = "Obtiene una vista simple de envíos",
            tags = {"3. DTOs"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado simple obtenido correctamente")
    })
    @GetMapping("/simpleDTO")
    public ResponseEntity<List<EnvioSimpleDTO>> obtenerSimpleDTO() {
        return ResponseEntity.ok(envioService.listarSimpleDTO());
    }

    @Operation(
            summary = "Obtener detalle DTO de envío",
            description = "Obtiene el detalle completo de un envío en formato DTO",
            tags = {"3. DTOs"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle del envío obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el envío")
    })
    @GetMapping("/{idEnvio}/detalleDTO")
    public ResponseEntity<EnvioDetalladoDTO> obtenerDetalleDTO(@PathVariable Integer idEnvio) {
        return ResponseEntity.ok(envioService.obtenerDetalleDTO(idEnvio));
    }
}
