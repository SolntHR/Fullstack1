package com.envio.despacho.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.envio.despacho.model.Envio;
import com.envio.despacho.service.EnvioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
@Validated
public class EnvioController {

    private final EnvioService envioService;

    @GetMapping
    public List<Envio> listar() {
        return envioService.listar();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Envio> buscar(@PathVariable Integer idEnvio) {
        return envioService.buscar(idEnvio);
    }

    @PostMapping
    public Envio guardar(@Valid @RequestBody Envio envio) {
        return envioService.guardar(envio);
    }

    @PutMapping("/actualizar/{id}")
    public Envio actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Envio envio) {

        return envioService.actualizar(id, envio);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        envioService.eliminar(id);
    }
}
