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

    // GET: OBTENER TODAS LAS PROMOCIONES
    @GetMapping("/listar")
    public List<Promociones> listarPromociones() {
        return service.listarPromociones();
    }

    // GET: BUSCAR PROMOCION POR ID
    @GetMapping("/buscar/{idPromocion}")
    public Optional<Promociones> buscarPorId(@PathVariable Integer idPromocion) {
        return service.buscarPorId(idPromocion);
    }

    // GET: BUSCAR PROMOCION POR NOMBRE
    @GetMapping("/buscar-nombre/{nombrePromocion}")
    public List<Promociones> buscarPorNombre(@PathVariable String nombrePromocion) {
        return service.buscarPorNombre(nombrePromocion);
    }

    // GET: BUSCAR PROMOCION POR CODIGO PROMOCIONAL
    @GetMapping("/buscar-codigo/{codigoPromocional}")
    public Optional<Promociones> buscarPorCodigo(@PathVariable String codigoPromocional) {
        return service.buscarPorCodigo(codigoPromocional);
    }

    // GET: BUSCAR PROMOCIONES POR FECHA DE INICIO
    @GetMapping("/buscar-fecha-inicio/{fechaInicio}")
    public List<Promociones> buscarPorFechaInicio(@PathVariable String fechaInicio) {
        return service.buscarPorFechaInicio(LocalDate.parse(fechaInicio)); 
        // Se convierte la fecha en formato String a LocalDate con LocalDate.parse()
    }

    // GET: BUSCAR PROMOCIONES POR RANGO DE FECHAS
    @GetMapping("/buscar-rango-fechas")
    public List<Promociones> buscarPorRangoFechas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        // se usa @RequestParam para recibir los parametros de fechaInicio y fechaFin desde la URL, 
        // por ejemplo: /buscar/rango-fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
        return service.buscarPorRangoFechas(LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin));
    }

    // GET: BUSCAR PROMOCIONES POR FECHA DE FIN
    @GetMapping("/buscar-fecha-fin/{fechaFin}")
    public List<Promociones> buscarPorFechaFin(@PathVariable String fechaFin) {
        return service.buscarPorFechaFin(LocalDate.parse(fechaFin));
    }

    // GET: BUSCAR PROMOCIONES POR MONTO MINIMO MAYOR A
    @GetMapping("/buscar-monto-minimo-mayor/{montoMinimo}")
    public List<Promociones> buscarPorMontoMinimo(@PathVariable String montoMinimo) {
        return service.buscarPorMontoMinimoMayor(new java.math.BigDecimal(montoMinimo));
    }

    // GET: BUSCAR PROMOCIONES POR MONTO MINIMO MENOR A
    @GetMapping("/buscar-monto-minimo-menor/{montoMinimo}")
    public List<Promociones> buscarPorMontoMinimoMenor(@PathVariable String montoMinimo) {
        return service.buscarPorMontoMinimoMenor(new java.math.BigDecimal(montoMinimo));
    }

    // GET: BUSCAR PROMOCIONES POR VECES DE USO MENOR A
    @GetMapping("/buscar-veces-uso-menor/{vecesUso}")
    public List<Promociones> buscarPorVecesUso(@PathVariable Integer vecesUso) {
        return service.buscarPorVecesUsoMenor(vecesUso);
    }

    // GET: BUSCAR PROMOCIONES POR VECES DE USO MAYOR A
    @GetMapping("/buscar-veces-uso-mayor/{vecesUso}")
    public List<Promociones> buscarPorVecesUsoMayor(@PathVariable Integer vecesUso) {
        return service.buscarPorVecesUsoMayor(vecesUso);
    }

    // GET: BUSCAR PROMOCIONES POR DESCUENTO MAYOR A
    @GetMapping("/buscar-descuento-mayor/{descuento}")
    public List<Promociones> buscarPorDescuentoMayor(@PathVariable String descuento) {
        return service.buscarPorDescuentoMayor(new java.math.BigDecimal(descuento));
        // se usa new java.math.BigDecimal(descuento) pq lo recibe como un String y lo convierte en formato
        //  decimal a BigDecimal, para que el service pueda procesarlo
    }

    // GET: BUSCAR PROMOCIONES POR DESCUENTO MENOR A
    @GetMapping("/buscar-descuento-menor/{descuento}")
    public List<Promociones> buscarPorDescuentoMenor(@PathVariable String descuento) {
        return service.buscarPorDescuentoMenor(new java.math.BigDecimal(descuento));
    }

    /*--------------------------------------------------------------------------------------------*/

    // POST: AGREGAR NUEVA PROMOCION
    @PostMapping("/agregar")
    public ResponseEntity<Promociones> agregarPromocion(@Valid @RequestBody Promociones promocion) {
        Promociones nuevaPromocion = service.agregarPromocion(promocion);
        return ResponseEntity.status(201).body(nuevaPromocion);
    }

    // PUT: ACTUALIZAR PROMOCION
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

    // DELETE: ELIMINAR PROMOCION
    @DeleteMapping("/eliminar/{idPromocion}")
    public ResponseEntity<String> eliminarPromocion(@PathVariable Integer idPromocion) {
        Optional<Promociones> promocionExistente = service.buscarPorId(idPromocion);
        if (promocionExistente.isPresent()) {
            service.eliminarPromocion(idPromocion);
            return ResponseEntity.status(200).body("Promoción eliminada exitosamente");
        }
        return ResponseEntity.status(404).body("No se pudo encontrar el id de la promocion");
    }

    // POST: APLICAR PROMOCION A UNA COMPRA
    @PostMapping("/aplicar")
    public ResponseEntity<BigDecimal> aplicarPromocion(@RequestParam String codigoPromocional, @RequestParam java.math.BigDecimal montoCompra) {
        BigDecimal montoFinal = service.aplicarPromocion(codigoPromocional, montoCompra);
        return ResponseEntity.status(200).body(montoFinal);
    }
    /*------------------------------------------------------------------------*/

    // GET: DTO

    @GetMapping("/listar-dto")
    public List<PromocionesSimpleDTO> ListarPromocionesSimpleDTO(){
        return service.listarPromocionesSimpleDTO();
    }
}
    