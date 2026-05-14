package com.microservicio.usuario.controller;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.service.RolService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService service;

    // GET: Lista a todos los roles
    @GetMapping("/listarRol")
    public List<Rol> listarRoles() {
        return service.listarRoles();
    }
    
    // GET: Buscar por ID
    @GetMapping("/rol/{idRol}")
    public Optional<Rol> buscarPorIdRol(@PathVariable Integer idRol) {
        return service.buscarPorIdRol(idRol);
    }

    // GET: Buscar por nombre
    @GetMapping("/nombre-rol/{nombreRol}")
    public Optional<Rol> buscarPorNombreRol(@PathVariable String nombreRol) {
        return service.buscarPorNombreRol(nombreRol);
    }

    // POST: Agregar un nuevo rol
    @PostMapping("/agregarRol")
    public ResponseEntity<Rol> agregarRol(@Valid @RequestBody Rol rol) {
        Rol nuevoRol = service.agregarRol(rol);
        return ResponseEntity.status(201).body(nuevoRol);
    }

    // PUT: Actualizar un rol existente
    @PutMapping("/actualizarRol/{idRol}")
    public String actualizarRol(@PathVariable Integer idRol, @RequestBody Rol rolActualizado) {
        Optional<Rol> rolExiste = service.buscarPorIdRol(idRol);
        if(rolExiste.isPresent()) {
            service.actualizarRol(idRol, rolActualizado);
            return "Rol actualizado exitosamente";
        }else {
            return "Rol no encontrado";
        }
    }

}
