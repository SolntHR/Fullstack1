package com.microservicio.usuario.controller;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.service.RolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RolService service;

    @Test
    void deberiaListarRoles() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        when(service.listarRoles()).thenReturn(List.of(rol));

        mockMvc.perform(get("/roles/listarRol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRol").value(1))
                .andExpect(jsonPath("$[0].nombreRol").value("ADMIN"));
    }

    @Test
    void deberiaBuscarRolPorId() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        when(service.buscarPorIdRol(1)).thenReturn(Optional.of(rol));

        mockMvc.perform(get("/roles/rol/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol").value(1))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void deberiaRetornar404AlBuscarRolPorIdSiNoExiste() throws Exception {
        when(service.buscarPorIdRol(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/roles/rol/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rol no encontrado"));
    }

    @Test
    void deberiaBuscarRolPorNombre() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        when(service.buscarPorNombreRol("ADMIN")).thenReturn(Optional.of(rol));

        mockMvc.perform(get("/roles/nombre-rol/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void deberiaCrearRol() throws Exception {
        Rol rolGuardado = new Rol(1, "ADMIN");
        
        String body = """
                {
                  "nombreRol": "ADMIN"
                }
                """;

        when(service.agregarRol(any(Rol.class))).thenReturn(rolGuardado);

        mockMvc.perform(post("/roles/agregarRol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRol").value(1))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void deberiaRetornar400SiLosDatosDelRolSonInvalidos() throws Exception {
        String body = """
                {
                  "nombreRol": ""
                }
                """;

        mockMvc.perform(post("/roles/agregarRol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.mensaje").value("Error de validación"))
                .andExpect(jsonPath("$.errores.nombreRol").exists());
    }

    @Test
    void deberiaRetornar409SiElRolYaExiste() throws Exception {
        String body = """
                {
                  "nombreRol": "ADMIN"
                }
                """;

        when(service.agregarRol(any(Rol.class)))
                .thenThrow(new IllegalArgumentException("El rol ya existe"));

        mockMvc.perform(post("/roles/agregarRol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.mensaje").value("El rol ya existe"));
    }

    @Test
    void deberiaActualizarRol() throws Exception {
        Rol actualizado = new Rol(1, "ADMIN_MOD");
        String body = """
                {
                  "nombreRol": "ADMIN_MOD"
                }
                """;

        when(service.actualizarRol(eq(1), any(Rol.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/roles/actualizarRol/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("Rol actualizado exitosamente"));
    }

    @Test
    void deberiaEliminarRol() throws Exception {
        when(service.eliminarRol(1)).thenReturn(true);

        mockMvc.perform(delete("/roles/eliminarRol/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rol eliminado exitosamente"));
    }

    @Test
    void deberiaRetornar404AlEliminarRolSiNoExiste() throws Exception {
        when(service.eliminarRol(99)).thenReturn(false);

        mockMvc.perform(delete("/roles/eliminarRol/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rol no encontrado"));
    }
}