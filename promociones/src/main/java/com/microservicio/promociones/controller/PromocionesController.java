package com.microservicio.promociones.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicio.promociones.dto.PromocionesSimpleDTO;

import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.service.PromocionesService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promociones")

public class PromocionesController {

    @Autowired
    private PromocionesService service;

    @GetMapping("/listar")
    public List<Promociones> listarPromociones() {
        return service.listarPromociones();
    }

    @GetMapping("/buscar/{idPromocion}")
    public Optional<Promociones> buscarPorId(@PathVariable Integer idPromocion) {
        return service.buscarPorId(idPromocion);
    }


    @GetMapping("/buscar-rango-fechas")
    public List<Promociones> buscarPorRangoFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        return service.buscarPorRangoFechas(LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin));
    }

    @PostMapping("/agregar")
    public ResponseEntity<Promociones> agregarPromocion(@Valid @RequestBody Promociones promocion) {
        Promociones nuevaPromocion = service.agregarPromocion(promocion);
        return ResponseEntity.status(201).body(nuevaPromocion);
    }

    @PutMapping("/actualizar/{idPromocion}")
    public ResponseEntity<Promociones> actualizarPromocion(@PathVariable Integer idPromocion, @Valid @RequestBody Promociones promocionActualizada) {
        Optional<Promociones> promocionExistente = service.buscarPorId(idPromocion);
        if (promocionExistente.isPresent()) {
            Promociones promocion = service.actualizarPromocion(idPromocion, promocionActualizada).orElseThrow();
            return ResponseEntity.status(200).body(promocion);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/eliminar/{idPromocion}")
    public ResponseEntity<String> eliminarPromocion(@PathVariable Integer idPromocion) {
        Optional<Promociones> promocionExistente = service.buscarPorId(idPromocion);
        if (promocionExistente.isPresent()) {
            service.eliminarPromocion(idPromocion);
            return ResponseEntity.status(200).body("Promoción eliminada exitosamente");
        }
        return ResponseEntity.status(404).body("No se pudo encontrar el id de la promocion");
    }

    @PostMapping("/aplicar")
    public ResponseEntity<BigDecimal> aplicarPromocion(@RequestParam String codigoPromocional, @RequestParam java.math.BigDecimal montoCompra) {
        BigDecimal montoFinal = service.aplicarPromocion(codigoPromocional, montoCompra);
        return ResponseEntity.status(200).body(montoFinal);
    }

    @GetMapping("/listar-dto")
    public List<PromocionesSimpleDTO> ListarPromocionesSimpleDTO(){
        return service.listarPromocionesSimpleDTO();
    }
}