package com.catalogo.inventario.controller;

import com.catalogo.inventario.dto.ProductoDetalleDTO;
import com.catalogo.inventario.dto.ProductoListadoDTO;
import com.catalogo.inventario.dto.ProductoSimpleDTO;
import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.service.ProductoService;
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

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductoService service;

    @Test
    @DisplayName("Debe listar todos los productos")
    void listar() throws Exception {
        Producto producto = crearProducto(1, "Laptop Gamer", 1500000, 10);

        when(service.listaProductos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/inventario/producto/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombreProducto").value("Laptop Gamer"));
    }

    @Test
    @DisplayName("Debe buscar producto por ID")
    void buscarPorId() throws Exception {
        Producto producto = crearProducto(1, "Laptop Gamer", 1500000, 10);

        when(service.buscarPorId(1)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/inventario/producto/productoI/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombreProducto").value("Laptop Gamer"));
    }

    @Test
    @DisplayName("Debe buscar productos por nombre")
    void buscarPorNombre() throws Exception {
        Producto producto = crearProducto(1, "Laptop Gamer", 1500000, 10);

        when(service.buscarPorNombre("Laptop Gamer")).thenReturn(List.of(producto));

        mockMvc.perform(get("/inventario/producto/productoN/Laptop Gamer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProducto").value("Laptop Gamer"));
    }

    @Test
    @DisplayName("Debe buscar productos por categoría")
    void buscarPorCategoria() throws Exception {
        Producto producto = crearProducto(1, "Laptop Gamer", 1500000, 10);

        when(service.buscarPorCategoria(1)).thenReturn(List.of(producto));

        mockMvc.perform(get("/inventario/producto/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1));
    }

    @Test
    @DisplayName("Debe agregar un producto")
    void crear() throws Exception {
        Producto entrada = crearProducto(null, "Mouse Inalámbrico", 25000, 50);
        Producto guardado = crearProducto(2, "Mouse Inalámbrico", 25000, 50);

        when(service.agregar(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(post("/inventario/producto/agregar-producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(2))
                .andExpect(jsonPath("$.nombreProducto").value("Mouse Inalámbrico"));
    }

    @Test
    @DisplayName("Debe actualizar un producto existente")
    void actualizar() throws Exception {
        Producto actualizado = crearProducto(null, "Laptop Actualizada", 1600000, 8);

        when(service.buscarPorId(1)).thenReturn(Optional.of(crearProducto(1, "Laptop Gamer", 1500000, 10)));
        when(service.productoUpdate(eq(1), any(Producto.class))).thenReturn(Optional.of(crearProducto(1, "Laptop Actualizada", 1600000, 8)));

        mockMvc.perform(put("/inventario/producto/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Producto actualizado correctamente"));
    }

    @Test
    @DisplayName("Debe eliminar un producto existente")
    void eliminar() throws Exception {
        when(service.buscarPorId(1)).thenReturn(Optional.of(crearProducto(1, "Laptop Gamer", 1500000, 10)));

        mockMvc.perform(delete("/inventario/producto/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Producto eliminado correctamente"));
    }

    @Test
    @DisplayName("Debe descontar stock de un producto")
    void descontar() throws Exception {
        Producto producto = crearProducto(1, "Laptop Gamer", 1500000, 10);

        when(service.buscarPorId(1)).thenReturn(Optional.of(producto));
        when(service.productoUpdate(eq(1), any(Producto.class))).thenReturn(Optional.of(producto));

        mockMvc.perform(put("/inventario/producto/1/descontar/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Stock actualizado. Nuevo stock: 7"));
    }

    @Test
    @DisplayName("Debe obtener listado DTO")
    void obtenerListado() throws Exception {
        ProductoListadoDTO dto = new ProductoListadoDTO();
        dto.setIdProducto(1);
        dto.setNombreProducto("Laptop Gamer");
        dto.setPrecioProducto(1500000);
        dto.setStockProducto(10);
        dto.setNombreCategoria("Electrónica");

        when(service.listarProductoDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/inventario/producto/listado-dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombreCategoria").value("Electrónica"));
    }

    @Test
    @DisplayName("Debe obtener listado simple DTO")
    void obtenerSimple() throws Exception {
        ProductoSimpleDTO dto = new ProductoSimpleDTO();
        dto.setIdProducto(1);
        dto.setNombreProducto("Laptop Gamer");

        when(service.listarProductoSimpleDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/inventario/producto/simple-dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombreProducto").value("Laptop Gamer"));
    }

    @Test
    @DisplayName("Debe obtener detalle DTO")
    void obtenerDetalle() throws Exception {
        ProductoDetalleDTO dto = new ProductoDetalleDTO();
        dto.setIdProducto(1);
        dto.setNombreProducto("Laptop Gamer");
        dto.setDescripcionProducto("Laptop de alto rendimiento para gaming");
        dto.setPrecioProducto(1500000);
        dto.setStockProducto(10);

        when(service.obtenerDetalleProducto(1)).thenReturn(dto);

        mockMvc.perform(get("/inventario/producto/1/detalle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombreProducto").value("Laptop Gamer"));
    }

    private Producto crearProducto(Integer id, String nombre, Integer precio, Integer stock) {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("Electrónica");

        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombreProducto(nombre);
        producto.setDescripcionProducto("Descripción de prueba para el producto");
        producto.setPrecioProducto(precio);
        producto.setStockProducto(stock);
        producto.setCategoria(categoria);
        return producto;
    }
}
