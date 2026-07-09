package com.microservicio.usuario.controller;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar roles",
            description = "Obtiene la lista completa de roles registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listarRol")
    public ResponseEntity<List<Rol>> listarRoles() {
        log.info("Solicitud recibida para listar roles");
        return ResponseEntity.ok(service.listarRoles());
    }

    @Operation(
            summary = "Buscar rol por id",
            description = "Busca un rol específico usando su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<?> buscarPorIdRol(@PathVariable Integer idRol) {
        log.info("Solicitud recibida para buscar rol con id: {}", idRol);

        Optional<Rol> rol = service.buscarPorIdRol(idRol);

        if (rol.isPresent()) {
            log.info("Respuesta exitosa para rol con id: {}", idRol);
            return ResponseEntity.ok(rol.get());
        }

        log.warn("Rol no encontrado en controller con id: {}", idRol);
        return ResponseEntity.status(404).body("Rol no encontrado");
    }

    @Operation(
            summary = "Buscar rol por nombre",
            description = "Busca un rol específico usando su nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre-rol/{nombreRol}")
    public ResponseEntity<?> buscarPorNombreRol(@PathVariable String nombreRol) {
        log.info("Solicitud recibida para buscar rol con nombre: {}", nombreRol);

        Optional<Rol> rol = service.buscarPorNombreRol(nombreRol);

        if (rol.isPresent()) {
            log.info("Respuesta exitosa para rol con nombre: {}", nombreRol);
            return ResponseEntity.ok(rol.get());
        }

        log.warn("Rol no encontrado en controller con nombre: {}", nombreRol);
        return ResponseEntity.status(404).body("Rol no encontrado");
    }

    @Operation(
            summary = "Crear rol",
            description = "Registra un nuevo rol en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El rol ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregarRol")
    public ResponseEntity<Rol> agregarRol(@Valid @RequestBody Rol rol) {
        log.info("Solicitud recibida para crear rol con nombre: {}", rol.getNombreRol());
        Rol nuevoRol = service.agregarRol(rol);
        log.info("Rol creado correctamente con id: {}", nuevoRol.getIdRol());
        return ResponseEntity.status(201).body(nuevoRol);
    }

    @Operation(
            summary = "Actualizar rol",
            description = "Actualiza los datos de un rol existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "409", description = "Ya existe un rol con ese nombre"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizarRol/{idRol}")
    public ResponseEntity<String> actualizarRol(@PathVariable Integer idRol, @Valid @RequestBody Rol rolActualizado) {
        log.info("Solicitud recibida para actualizar rol con id: {}", idRol);

        Optional<Rol> rolActualizadoOpt = service.actualizarRol(idRol, rolActualizado);

        if (rolActualizadoOpt.isPresent()) {
            log.info("Rol actualizado correctamente con id: {}", idRol);
            return ResponseEntity.ok("Rol actualizado exitosamente");
        }

        log.warn("No se pudo actualizar. Rol no encontrado con id: {}", idRol);
        return ResponseEntity.status(404).body("Rol no encontrado");
    }

    @Operation(
            summary = "Eliminar rol",
            description = "Elimina un rol existente según su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminarRol/{idRol}")
    public ResponseEntity<String> eliminarRol(@PathVariable Integer idRol) {
        log.info("Solicitud recibida para eliminar rol con id: {}", idRol);

        boolean eliminado = service.eliminarRol(idRol);

        if (eliminado) {
            log.info("Rol eliminado correctamente con id: {}", idRol);
            return ResponseEntity.ok("Rol eliminado exitosamente");
        }

        log.warn("No se pudo eliminar. Rol no encontrado con id: {}", idRol);
        return ResponseEntity.status(404).body("Rol no encontrado");
    }
}