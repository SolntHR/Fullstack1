package com.catalogo.inventario.controller;

import com.catalogo.inventario.dto.CategoriaDetalleDTO;
import com.catalogo.inventario.dto.CategoriaListadoDTO;
import com.catalogo.inventario.dto.CategoriaSimpleDTO;
import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CategoriaService service;

    @Test
    @DisplayName("Debe listar todas las categorías")
    void listarCategoria() throws Exception {
        Categoria categoria = crearCategoria(1, "Electrónica");

        when(service.listarCategoria()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/inventario/categoria/listarcategoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCategoria").value(1))
                .andExpect(jsonPath("$[0].nombreCategoria").value("Electrónica"));
    }

    @Test
    @DisplayName("Debe buscar categoría por ID")
    void buscarPorId() throws Exception {
        Categoria categoria = crearCategoria(1, "Electrónica");

        when(service.idCategoria(1)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/inventario/categoria/categoriaI/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria").value(1))
                .andExpect(jsonPath("$.nombreCategoria").value("Electrónica"));
    }

    @Test
    @DisplayName("Debe buscar categoría por nombre")
    void buscarPorNombre() throws Exception {
        Categoria categoria = crearCategoria(1, "Electrónica");

        when(service.nombreCategoria("Electrónica")).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/inventario/categoria/categoriaN/Electrónica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCategoria").value("Electrónica"));
    }

    @Test
    @DisplayName("Debe agregar una categoría")
    void crear() throws Exception {
        Categoria entrada = crearCategoria(null, "Hogar");
        Categoria guardada = crearCategoria(2, "Hogar");

        when(service.guardarCategoria(any(Categoria.class))).thenReturn(guardada);

        mockMvc.perform(post("/inventario/categoria/agregar-categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria").value(2))
                .andExpect(jsonPath("$.nombreCategoria").value("Hogar"));
    }

    @Test
    @DisplayName("Debe actualizar una categoría existente")
    void actualizar() throws Exception {
        Categoria actualizada = crearCategoria(null, "Electrónica Premium");

        when(service.idCategoria(1)).thenReturn(Optional.of(crearCategoria(1, "Electrónica")));
        when(service.actualizarCategoria(eq(1), any(Categoria.class)))
                .thenReturn(Optional.of(crearCategoria(1, "Electrónica Premium")));

        mockMvc.perform(put("/inventario/categoria/actualizar-categoria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Categoria Actualizada Correctamente"));
    }

    @Test
    @DisplayName("Debe eliminar una categoría existente")
    void eliminarCategoria() throws Exception {
        when(service.idCategoria(1)).thenReturn(Optional.of(crearCategoria(1, "Electrónica")));

        mockMvc.perform(delete("/inventario/categoria/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Categoria eliminada correctamente"));
    }

    @Test
    @DisplayName("Debe obtener listado DTO de categorías")
    void listarDTO() throws Exception {
        CategoriaListadoDTO dto = new CategoriaListadoDTO();
        dto.setIdCategoria(1);
        dto.setNombreCategoria("Electrónica");

        when(service.listarDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/inventario/categoria/listar-dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCategoria").value(1))
                .andExpect(jsonPath("$[0].nombreCategoria").value("Electrónica"));
    }

    @Test
    @DisplayName("Debe obtener detalle simple DTO")
    void obtenerDetalleSimple() throws Exception {
        CategoriaSimpleDTO dto = new CategoriaSimpleDTO();
        dto.setIdCategoria(1);
        dto.setNombreCategoria("Electrónica");
        dto.setProducto(List.of("Laptop Gamer", "Mouse Inalámbrico"));

        when(service.obtenerDetalleSimple(1)).thenReturn(dto);

        mockMvc.perform(get("/inventario/categoria/1/detalle-simple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria").value(1))
                .andExpect(jsonPath("$.producto[0]").value("Laptop Gamer"));
    }

    @Test
    @DisplayName("Debe obtener detalle completo DTO")
    void obtenerDetalleCompleto() throws Exception {
        CategoriaDetalleDTO dto = new CategoriaDetalleDTO();
        dto.setIdCategoria(1);
        dto.setNombreCategoria("Electrónica");

        when(service.obtenerCategoriaConProductos(1)).thenReturn(dto);

        mockMvc.perform(get("/inventario/categoria/1/detalle-completo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria").value(1))
                .andExpect(jsonPath("$.nombreCategoria").value("Electrónica"));
    }

    private Categoria crearCategoria(Integer id, String nombre) {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(id);
        categoria.setNombreCategoria(nombre);
        return categoria;
    }
}
