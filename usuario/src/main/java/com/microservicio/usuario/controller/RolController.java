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

    @GetMapping("/listarRol")
    public List<Rol> listarRoles() {
        return service.listarRoles();
    }
    
    @GetMapping("/rol/{idRol}")
    public Optional<Rol> buscarPorIdRol(@PathVariable Integer idRol) {
        return service.buscarPorIdRol(idRol);
    }

    @GetMapping("/nombre-rol/{nombreRol}")
    public Optional<Rol> buscarPorNombreRol(@PathVariable String nombreRol) {
        return service.buscarPorNombreRol(nombreRol);
    }

    @PostMapping("/agregarRol")
    public ResponseEntity<Rol> agregarRol(@Valid @RequestBody Rol rol) {
        Rol nuevoRol = service.agregarRol(rol);
        return ResponseEntity.status(201).body(nuevoRol);
    }

    @PutMapping("/actualizarRol/{idRol}")
    public ResponseEntity<String> actualizarRol(@PathVariable Integer idRol, @Valid @RequestBody Rol rolActualizado) {
        Optional<Rol> rolExiste = service.buscarPorIdRol(idRol);
        if(rolExiste.isPresent()) {
            service.actualizarRol(idRol, rolActualizado);
            return ResponseEntity.status(200).body("Rol actualizado exitosamente");
        }else {
            return ResponseEntity.status(404).body("Rol no encontrado");
        }
    }

}
