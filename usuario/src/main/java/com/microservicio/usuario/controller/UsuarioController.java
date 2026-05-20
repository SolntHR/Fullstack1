package com.microservicio.usuario.controller;

import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicio.usuario.dto.UsuarioDTO.UsuarioSimpleDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // GET: Lista a todos los usuarios
    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return service.listarUsuarios();
    }

    // GET: Buscar por ID
    @GetMapping("/user/{idUsuario}")
    public Optional<Usuario> buscarPorId(@PathVariable Integer idUsuario) {
        return service.buscarPorId(idUsuario);
    }

    // GET: Buscar por nombre
    @GetMapping("/nombre/{nombre}")
    public List<Usuario> buscarPorNombre(@PathVariable String nombre) {
        return service.buscarPorNombre(nombre);
    }

    // GET:Buscar por apellido
    @GetMapping("/apellido/{apellido}")
    public List<Usuario> buscarPorApellido(@PathVariable String apellido) {
        return service.buscarPorApellido(apellido);
    }

    // GET: Buscar por email
    @GetMapping("/email/{email}")
    public Optional<Usuario> buscarPorEmail(@PathVariable String email) {
        return service.buscarPorEmail(email);
    }

    // POST: Agregar un nuevo usuario
    @PostMapping("/agregar")
    public ResponseEntity<Usuario> agregarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = service.agregarUsuario(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
    
    // PUT: Actualizar un usuario existente
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Integer idUsuario, @Valid @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuario = service.actualizarUsuario(idUsuario, usuarioActualizado);
        if(usuario.isPresent()) {
            return ResponseEntity.status(200).body("Usuario actualizado exitosamente");
        }
        return ResponseEntity.status(404).body("Usuario no encontrado" + idUsuario);
    }

    // DELETE: Eliminar un usuario por ID
    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer idUsuario) {
        Optional<Usuario> usuario = service.buscarPorId(idUsuario);
        if(usuario.isPresent()) {
            service.eliminarUsuario(idUsuario);
            return ResponseEntity.status(200).body("Usuario eliminado exitosamente");
        }
        return ResponseEntity.status(404).body("Usuario no encontrado" + idUsuario);
    }

    // GET: Lista a todos los usuarios con su rol (DTO)
    @GetMapping("/{idUsuario}/detalle-simple")
    public UsuarioSimpleDTO obtenerDetalleSimple(@PathVariable Integer idUsuario) {
        return service.obtenerDetalleSimple(idUsuario);
    }

}
