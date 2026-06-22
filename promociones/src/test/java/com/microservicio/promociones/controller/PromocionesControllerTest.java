package com.microservicio.promociones.controller;

import com.microservicio.promociones.dto.PromocionesSimpleDTO;
import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.service.PromocionesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromocionesController.class)
class PromocionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PromocionesService service;

    @Test
    void deberiaListarPromociones() throws Exception {
        Promociones promo = new Promociones();
        promo.setIdPromocion(1);
        promo.setNombrePromocion("Promo Pizza");
        promo.setCodigoPromocional("PIZZA20");
        promo.setDescuento(new BigDecimal("20.0"));
        promo.setFechaInicio(LocalDate.of(2026, 6, 1));
        promo.setFechaFin(LocalDate.of(2026, 6, 30));
        promo.setVecesUso(100);
        promo.setMontoMinimo(new BigDecimal("5000.0"));

        when(service.listarPromociones()).thenReturn(List.of(promo));

        mockMvc.perform(get("/promociones/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPromocion").value(1))
                .andExpect(jsonPath("$[0].nombrePromocion").value("Promo Pizza"))
                .andExpect(jsonPath("$[0].codigoPromocional").value("PIZZA20"))
                .andExpect(jsonPath("$[0].descuento").value(20.0));
    }

    @Test
    void deberiaBuscarPromocionPorId() throws Exception {
        Promociones promo = new Promociones();
        promo.setIdPromocion(1);
        promo.setNombrePromocion("Promo Pizza");
        promo.setCodigoPromocional("PIZZA20");
        promo.setDescuento(new BigDecimal("20.0"));
        promo.setFechaInicio(LocalDate.of(2026, 6, 1));
        promo.setFechaFin(LocalDate.of(2026, 6, 30));

        when(service.buscarPorId(1)).thenReturn(Optional.of(promo));

        mockMvc.perform(get("/promociones/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPromocion").value(1))
                .andExpect(jsonPath("$.nombrePromocion").value("Promo Pizza"))
                .andExpect(jsonPath("$.codigoPromocional").value("PIZZA20"));
    }

    @Test
    void deberiaRetornar404SiPromocionNoExistePorId() throws Exception {
        when(service.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/promociones/buscar/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Promoción no encontrada"));
    }

    @Test
    void deberiaBuscarPorRangoFechas() throws Exception {
        Promociones promo = new Promociones();
        promo.setIdPromocion(1);
        promo.setNombrePromocion("Promo Invierno");
        promo.setCodigoPromocional("WINTER");
        promo.setDescuento(new BigDecimal("15.0"));
        promo.setFechaInicio(LocalDate.of(2026, 6, 1));
        promo.setFechaFin(LocalDate.of(2026, 6, 30));

        when(service.buscarPorRangoFechas(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(promo));

        mockMvc.perform(get("/promociones/buscar-rango-fechas")
                        .param("fechaInicio", "2026-06-01")
                        .param("fechaFin", "2026-06-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombrePromocion").value("Promo Invierno"));
    }

    @Test
    void deberiaCrearPromocion() throws Exception {
        Promociones promoGuardada = new Promociones();
        promoGuardada.setIdPromocion(12);
        promoGuardada.setNombrePromocion("Descuento Nuevo");
        promoGuardada.setCodigoPromocional("NEW12");
        promoGuardada.setDescuento(new BigDecimal("12.0"));
        promoGuardada.setMontoMinimo(new BigDecimal("5000.0"));
        String bodyJson = """
                {
                  "nombrePromocion": "Descuento Nuevo",
                  "codigoPromocional": "NEW12",
                  "descuento": 12.0,
                  "fechaInicio": "2026-06-01",
                  "fechaFin": "2026-06-30",
                  "vecesUso": 100,
                  "montoMinimo": 5000.0
                }
                """;

        when(service.agregarPromocion(any(Promociones.class))).thenReturn(promoGuardada);

        mockMvc.perform(post("/promociones/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Promoción creada exitosamente con el id: 12"));
    }

    @Test
    void deberiaActualizarPromocion() throws Exception {
        Promociones promoActualizada = new Promociones();
        promoActualizada.setIdPromocion(1);
        promoActualizada.setNombrePromocion("Promo Refactor");
        promoActualizada.setCodigoPromocional("PIZZA25");
        promoActualizada.setDescuento(new BigDecimal("25.0"));

        String bodyJson = """
                {
                  "nombrePromocion": "Promo Refactor",
                  "codigoPromocional": "PIZZA25",
                  "descuento": 25.0,
                  "fechaInicio": "2026-06-01",
                  "fechaFin": "2026-06-30",
                  "vecesUso": 150,
                  "montoMinimo": 6000.0
                }
                """;

        when(service.buscarPorId(1)).thenReturn(Optional.of(promoActualizada));
        when(service.actualizarPromocion(eq(1), any(Promociones.class))).thenReturn(Optional.of(promoActualizada));

        mockMvc.perform(put("/promociones/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Promoción actualizada exitosamente"));
    }


    @Test
    void deberiaEliminarPromocion() throws Exception {
        Promociones promo = new Promociones();
        promo.setIdPromocion(1);
        promo.setNombrePromocion("A Borrar");
        promo.setCodigoPromocional("DELETE_ME");

        when(service.buscarPorId(1)).thenReturn(Optional.of(promo));
        when(service.eliminarPromocion(1)).thenReturn(true);

        mockMvc.perform(delete("/promociones/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Promoción eliminada exitosamente"));
    }

    @Test
    void deberiaRetornar404AlEliminarSiNoExiste() throws Exception {
        when(service.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/promociones/eliminar/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se pudo encontrar el id de la promoción"));
    }

    @Test
    void deberiaListarPromocionesSimpleDTO() throws Exception {
        PromocionesSimpleDTO dto = new PromocionesSimpleDTO();
        dto.setNombrePromocion("Simple Promo");
        dto.setCodigoPromocional("SMPL");
        dto.setDescuento(new BigDecimal("10.0"));

        when(service.listarPromocionesSimpleDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/promociones/listar-dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombrePromocion").value("Simple Promo"))
                .andExpect(jsonPath("$[0].codigoPromocional").value("SMPL"))
                .andExpect(jsonPath("$[0].descuento").value(10.0));
    }
}