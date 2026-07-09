package com.microservicio.usuario.controller;

import com.microservicio.usuario.dto.UsuarioDTO.UsuarioSimpleDTO;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Slf4j
@Tag(name = "Usuario", description = "Operaciones de consulta y gestión de usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene la lista completa de usuarios registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        log.info("Solicitud recibida para listar usuarios");
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @Operation(
            summary = "Buscar usuario por id",
            description = "Busca un usuario específico a partir de su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/user/{idUsuario}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer idUsuario) {
        log.info("Solicitud recibida para buscar usuario con id: {}", idUsuario);

        Optional<Usuario> usuario = service.buscarPorId(idUsuario);

        if (usuario.isPresent()) {
            log.info("Usuario encontrado en controller con id: {}", idUsuario);
            return ResponseEntity.ok(usuario.get());
        }

        log.warn("Usuario no encontrado en controller con id: {}", idUsuario);
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @Operation(
            summary = "Verificar existencia de usuario",
            description = "Verifica si existe un usuario según su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/existe/{idUsuario}")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Integer idUsuario) {
        log.info("Solicitud recibida para verificar existencia de usuario con id: {}", idUsuario);
        boolean existe = service.buscarPorId(idUsuario).isPresent();
        log.info("Resultado de existencia para id {}: {}", idUsuario, existe);
        return ResponseEntity.ok(existe);
    }

    @Operation(
            summary = "Buscar usuarios por nombre",
            description = "Obtiene la lista de usuarios que coinciden con el nombre ingresado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@PathVariable String nombre) {
        log.info("Solicitud recibida para buscar usuarios por nombre: {}", nombre);
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @Operation(
            summary = "Buscar usuarios por apellido",
            description = "Obtiene la lista de usuarios que coinciden con el apellido ingresado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Usuario>> buscarPorApellido(@PathVariable String apellido) {
        log.info("Solicitud recibida para buscar usuarios por apellido: {}", apellido);
        return ResponseEntity.ok(service.buscarPorApellido(apellido));
    }

    @Operation(
            summary = "Buscar usuario por email",
            description = "Busca un usuario específico a partir de su correo electrónico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        log.info("Solicitud recibida para buscar usuario por email: {}", email);

        Optional<Usuario> usuario = service.buscarPorEmail(email);

        if (usuario.isPresent()) {
            log.info("Usuario encontrado en controller con email: {}", email);
            return ResponseEntity.ok(usuario.get());
        }

        log.warn("Usuario no encontrado en controller con email: {}", email);
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El email ya está registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarUsuario(@Valid @RequestBody Usuario usuario) {
        log.info("Solicitud recibida para crear usuario con email: {}", usuario.getEmail());
        Usuario nuevoUsuario = service.agregarUsuario(usuario);
        log.info("Usuario creado correctamente con id: {}", nuevoUsuario.getIdUsuario());
        return ResponseEntity.status(201).body("Usuario creado exitosamente con el id: " + nuevoUsuario.getIdUsuario());
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza los datos de un usuario existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "El email ya está registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Integer idUsuario,
                                                    @Valid @RequestBody Usuario usuarioActualizado) {
        log.info("Solicitud recibida para actualizar usuario con id: {}", idUsuario);

        Optional<Usuario> usuario = service.actualizarUsuario(idUsuario, usuarioActualizado);

        if (usuario.isPresent()) {
            log.info("Usuario actualizado correctamente con id: {}", idUsuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        }

        log.warn("No se pudo actualizar. Usuario no encontrado con id: {}", idUsuario);
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario existente según su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer idUsuario) {
        log.info("Solicitud recibida para eliminar usuario con id: {}", idUsuario);

        boolean eliminado = service.eliminarUsuario(idUsuario);

        if (eliminado) {
            log.info("Usuario eliminado correctamente con id: {}", idUsuario);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        }

        log.warn("No se pudo eliminar. Usuario no encontrado con id: {}", idUsuario);
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    @Operation(
            summary = "Obtener detalle simple de usuario",
            description = "Obtiene un resumen del usuario con sus datos básicos y el nombre del rol"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle simple obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{idUsuario}/detalle-simple")
    public ResponseEntity<?> obtenerDetalleSimple(@PathVariable Integer idUsuario) {
        log.info("Solicitud recibida para obtener detalle simple del usuario con id: {}", idUsuario);

        UsuarioSimpleDTO usuarioDTO = service.obtenerDetalleSimple(idUsuario);

        if (usuarioDTO != null) {
            log.info("Detalle simple generado correctamente para usuario con id: {}", idUsuario);
            return ResponseEntity.ok(usuarioDTO);
        }

        log.warn("No se pudo generar detalle simple. Usuario no encontrado con id: {}", idUsuario);
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
}