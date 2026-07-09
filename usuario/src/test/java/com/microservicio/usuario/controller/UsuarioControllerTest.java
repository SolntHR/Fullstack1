package com.microservicio.usuario.controller;

import com.microservicio.usuario.dto.UsuarioDTO.UsuarioSimpleDTO;
import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.service.UsuarioService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;

    @Test
    void deberiaListarUsuarios() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuario = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        when(service.listarUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellido").value("Perez"))
                .andExpect(jsonPath("$[0].email").value("juan@gmail.com"))
                .andExpect(jsonPath("$[0].rol.idRol").value(1))
                .andExpect(jsonPath("$[0].rol.nombreRol").value("ADMIN"));
    }

    @Test
    void deberiaBuscarUsuarioPorId() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuario = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        when(service.buscarPorId(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
        void deberiaBuscarUsuarioPorIdSiNoExiste() throws Exception {
                when(service.buscarPorId(99)).thenReturn(Optional.empty());

                mockMvc.perform(get("/usuarios/user/99"))
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("Usuario no encontrado"));
        }

    @Test
    void deberiaRetornarTrueSiExisteUsuario() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuario = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        when(service.buscarPorId(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/existe/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deberiaRetornarFalseSiNoExisteUsuario() throws Exception {
        when(service.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/existe/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void deberiaBuscarUsuariosPorNombre() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuario = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        when(service.buscarPorNombre("Juan")).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios/nombre/Juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void deberiaBuscarUsuariosPorApellido() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuario = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        when(service.buscarPorApellido("Perez")).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios/apellido/Perez"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apellido").value("Perez"));
    }

    @Test
    void deberiaBuscarUsuarioPorEmail() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuario = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        when(service.buscarPorEmail("juan@gmail.com")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/email/juan@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
    void deberiaRetornar404AlBuscarUsuarioPorEmailSiNoExiste() throws Exception {
        when(service.buscarPorEmail("noexiste@gmail.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/email/noexiste@gmail.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void deberiaCrearUsuario() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuarioGuardado = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "123456", rol, null);

        String body = """
                {
                  "nombre": "Juan",
                  "apellido": "Perez",
                  "email": "juan@gmail.com",
                  "password": "123456",
                  "rol": {
                    "idRol": 1,
                    "nombreRol": "ADMIN"
                  }
                }
                """;

        when(service.agregarUsuario(any(Usuario.class))).thenReturn(usuarioGuardado);

        mockMvc.perform(post("/usuarios/agregar")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
            .andExpect(status().isCreated())
            .andExpect(content().string("Usuario creado exitosamente con el id: " + usuarioGuardado.getIdUsuario()));
    }

    @Test
    void deberiaRetornarDatosDelUsuarioSonInvalidos() throws Exception {
        String body = """
                {
                  "nombre": "",
                  "apellido": "",
                  "email": "correo-invalido",
                  "password": "123",
                  "rol": null
                }
                """;

        mockMvc.perform(post("/usuarios/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.mensaje").value("Error de validación"))
                .andExpect(jsonPath("$.errores.nombre").exists())
                .andExpect(jsonPath("$.errores.apellido").exists())
                .andExpect(jsonPath("$.errores.email").exists())
                .andExpect(jsonPath("$.errores.password").exists())
                .andExpect(jsonPath("$.errores.rol").exists());
    }

    @Test
    void deberiaRetornarEmailYaEstaRegistrado() throws Exception {
        String body = """
                {
                  "nombre": "Juan",
                  "apellido": "Perez",
                  "email": "juan@gmail.com",
                  "password": "123456",
                  "rol": {
                    "idRol": 1,
                    "nombreRol": "ADMIN"
                  }
                }
                """;

        when(service.agregarUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("El email ya está registrado"));

        mockMvc.perform(post("/usuarios/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.mensaje").value("El email ya está registrado"));
    }

    @Test
    void deberiaActualizarUsuario() throws Exception {
        Rol rol = new Rol(1, "ADMIN");
        Usuario usuarioActualizado = new Usuario(1, "Juan", "Perez", "juan@gmail.com", "654321", rol, null);

        String body = """
                {
                  "nombre": "Juan",
                  "apellido": "Perez",
                  "email": "juan@gmail.com",
                  "password": "654321",
                  "rol": {
                    "idRol": 1,
                    "nombreRol": "ADMIN"
                  }
                }
                """;

        when(service.actualizarUsuario(eq(1), any(Usuario.class)))
                .thenReturn(Optional.of(usuarioActualizado));

        mockMvc.perform(put("/usuarios/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario actualizado exitosamente"));
    }

    @Test
    void deberiaRetornar404AlActualizarUsuarioSiNoExiste() throws Exception {
        String body = """
                {
                  "nombre": "Juan",
                  "apellido": "Perez",
                  "email": "juan@gmail.com",
                  "password": "654321",
                  "rol": {
                    "idRol": 1,
                    "nombreRol": "ADMIN"
                  }
                }
                """;

        when(service.actualizarUsuario(eq(99), any(Usuario.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/usuarios/actualizar/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void deberiaRetornar409AlActualizarUsuarioSiElEmailYaExiste() throws Exception {
        String body = """
                {
                  "nombre": "Juan",
                  "apellido": "Perez",
                  "email": "repetido@gmail.com",
                  "password": "654321",
                  "rol": {
                    "idRol": 1,
                    "nombreRol": "ADMIN"
                  }
                }
                """;

        when(service.actualizarUsuario(eq(1), any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("Ya existe un usuario con ese email"));

        mockMvc.perform(put("/usuarios/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.mensaje").value("Ya existe un usuario con ese email"));
    }

    @Test
    void deberiaEliminarUsuario() throws Exception {
        when(service.eliminarUsuario(1)).thenReturn(true);

        mockMvc.perform(delete("/usuarios/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario eliminado exitosamente"));
    }

    @Test
    void deberiaRetornar404AlEliminarUsuarioSiNoExiste() throws Exception {
        when(service.eliminarUsuario(99)).thenReturn(false);

        mockMvc.perform(delete("/usuarios/eliminar/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void deberiaObtenerDetalleSimple() throws Exception {
        UsuarioSimpleDTO dto = new UsuarioSimpleDTO();
        dto.setIdUsuario(1);
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setNombreRol("ADMIN");

        when(service.obtenerDetalleSimple(1)).thenReturn(dto);

        mockMvc.perform(get("/usuarios/1/detalle-simple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void deberiaRetornar404SiNoExisteDetalleSimple() throws Exception {
        when(service.obtenerDetalleSimple(99)).thenReturn(null);

        mockMvc.perform(get("/usuarios/99/detalle-simple"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }
}