package com.microservicio.reportes.controller;

import com.microservicio.reportes.model.Pago;
import com.microservicio.reportes.model.Reportes;
import com.microservicio.reportes.service.ReportesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportesController.class)
public class ReportesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportesService service;

    @Test
    void deberialistarTodosLosReportes() throws Exception {
        List<Reportes> listaFalsa = new ArrayList<>();
        Reportes r1 = new Reportes();
        r1.setIdReporte(1);
        r1.setNombreReporte("Reporte 1");
        listaFalsa.add(r1);

        when(service.listarTodosLosReportes()).thenReturn(listaFalsa);

        mockMvc.perform(get("/reportes/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreReporte").value("Reporte 1"));
    }

    @Test
    void buscarReportePorId() throws Exception {
        Reportes r = new Reportes();
        r.setIdReporte(10);
        r.setNombreReporte("Reporte Encontrado");

        when(service.buscarReportePorId(10)).thenReturn(Optional.of(r));

        mockMvc.perform(get("/reportes/buscar-por-id/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreReporte").value("Reporte Encontrado"));
    }

    @Test
    void DeberiabuscaReportePorIdNoEncontrado() throws Exception {
        when(service.buscarReportePorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reportes/buscar-por-id/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void DeberiaAgregarReporte() throws Exception {

        String reporteJson = """
            {
                "nombreReporte": "Ventas Cyber",
                "descripcionReporte": "Detalles del Cyber de mayo",
                "tipoReporte": "VENTAS_MENSUAL",
                "fechaInicio": "2026-05-25T00:00:00",
                "fechaFin": "2026-05-28T23:59:59"
            }
            """;

        Reportes mockCreado = new Reportes();
        mockCreado.setIdReporte(55);

        when(service.agregarReporte(any(Reportes.class))).thenReturn(mockCreado);
        
        mockMvc.perform(post("/reportes/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reporteJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Reporte creado exitosamente: 55"));
    }

    @Test
    void deberiaEliminarReporte() throws Exception {
        when(service.eliminarReporte(1)).thenReturn(true);

        mockMvc.perform(delete("/reportes/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reporte eliminado exitosamente."));
    }

    @Test
    void DeberiaEliminarReporte_CuandoNoExiste() throws Exception {
        when(service.eliminarReporte(2)).thenReturn(false);

        mockMvc.perform(delete("/reportes/eliminar/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reporte no encontrado."));
    }

    @Test
    void DeberiaVerPagosDelReporte() throws Exception {
        List<Pago> pagosFalsos = new ArrayList<>();
        Pago p1 = new Pago();
        p1.setId(100);
        p1.setMonto(25000);
        pagosFalsos.add(p1);

        when(service.obtenerPagos(5)).thenReturn(pagosFalsos);

        mockMvc.perform(get("/reportes/5/ver-pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].monto").value(25000));
    }
}