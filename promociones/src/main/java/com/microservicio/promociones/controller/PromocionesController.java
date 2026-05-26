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

    @GetMapping("/buscar-nombre/{nombrePromocion}")
    public List<Promociones> buscarPorNombre(@PathVariable String nombrePromocion) {
        return service.buscarPorNombre(nombrePromocion);
    }

    @GetMapping("/buscar-codigo/{codigoPromocional}")
    public Optional<Promociones> buscarPorCodigo(@PathVariable String codigoPromocional) {
        return service.buscarPorCodigo(codigoPromocional);
    }

    @GetMapping("/buscar-fecha-inicio/{fechaInicio}")
    public List<Promociones> buscarPorFechaInicio(@PathVariable String fechaInicio) {
        return service.buscarPorFechaInicio(LocalDate.parse(fechaInicio)); 
    }

    @GetMapping("/buscar-rango-fechas")
    public List<Promociones> buscarPorRangoFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        return service.buscarPorRangoFechas(LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin));
    }

    @GetMapping("/buscar-fecha-fin/{fechaFin}")
    public List<Promociones> buscarPorFechaFin(@PathVariable String fechaFin) {
        return service.buscarPorFechaFin(LocalDate.parse(fechaFin));
    }

    @GetMapping("/buscar-monto-minimo-mayor/{montoMinimo}")
    public List<Promociones> buscarPorMontoMinimo(@PathVariable String montoMinimo) {
        return service.buscarPorMontoMinimoMayor(new java.math.BigDecimal(montoMinimo));
    }

    @GetMapping("/buscar-monto-minimo-menor/{montoMinimo}")
    public List<Promociones> buscarPorMontoMinimoMenor(@PathVariable String montoMinimo) {
        return service.buscarPorMontoMinimoMenor(new java.math.BigDecimal(montoMinimo));
    }

    @GetMapping("/buscar-veces-uso-menor/{vecesUso}")
    public List<Promociones> buscarPorVecesUso(@PathVariable Integer vecesUso) {
        return service.buscarPorVecesUsoMenor(vecesUso);
    }

    @GetMapping("/buscar-veces-uso-mayor/{vecesUso}")
    public List<Promociones> buscarPorVecesUsoMayor(@PathVariable Integer vecesUso) {
        return service.buscarPorVecesUsoMayor(vecesUso);
    }

    @GetMapping("/buscar-descuento-mayor/{descuento}")
    public List<Promociones> buscarPorDescuentoMayor(@PathVariable String descuento) {
        return service.buscarPorDescuentoMayor(new java.math.BigDecimal(descuento));
    }

    @GetMapping("/buscar-descuento-menor/{descuento}")
    public List<Promociones> buscarPorDescuentoMenor(@PathVariable String descuento) {
        return service.buscarPorDescuentoMenor(new java.math.BigDecimal(descuento));
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