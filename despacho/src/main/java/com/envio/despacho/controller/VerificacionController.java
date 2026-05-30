package com.envio.despacho.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.envio.despacho.model.Verificacion;
import com.envio.despacho.service.VerificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/verificaciones")
@RequiredArgsConstructor
public class VerificacionController {

    private final VerificacionService service;

    @GetMapping
    public List<Verificacion> listar() {
        return service.listar();
    }

    @GetMapping("/buscar/{id}")
    public Verificacion buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public Verificacion guardar(@Valid @RequestBody Verificacion verificacion) {
        return service.guardar(verificacion);
    }

    @PutMapping("/actualizar/{id}")
    public Verificacion actualizar(@PathVariable Integer id,@Valid @RequestBody Verificacion verificacion) {
        return service.actualizar(id, verificacion);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
