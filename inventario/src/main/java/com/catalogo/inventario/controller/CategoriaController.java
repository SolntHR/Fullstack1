package com.catalogo.inventario.controller;

import com.catalogo.inventario.dto.CategoriaDetalleDTO;
import com.catalogo.inventario.dto.CategoriaListadoDTO;
import com.catalogo.inventario.dto.CategoriaSimpleDTO;
import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventario/categoria")
public class CategoriaController {

    private final CategoriaService service;

    CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas las categorías", description = "Obtiene la lista completa de categorías registradas", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente")
    })
    @GetMapping("/listarcategoria")
    public List<Categoria> listarCategoria() {
        return service.listarCategoria();
    }

    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría específica a partir de su identificador", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría")
    })
    @GetMapping("/categoriaI/{idCategoria}")
    public Optional<Categoria> buscarPorId(@PathVariable Integer idCategoria) {
        return service.idCategoria(idCategoria);
    }

    @Operation(summary = "Buscar categoría por nombre", description = "Obtiene una categoría a partir de su nombre", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría")
    })
    @GetMapping("/categoriaN/{nombreCategoria}")
    public Optional<Categoria> buscarPorNombre(@PathVariable String nombreCategoria) {
        return service.nombreCategoria(nombreCategoria);
    }

    @Operation(summary = "Agregar una categoría", description = "Crea una nueva categoría en el catálogo", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping("/agregar-categoria")
    public Categoria crear(@Valid @RequestBody Categoria categoria) {
        return service.guardarCategoria(categoria);
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría")
    })
    @PutMapping("/actualizar-categoria/{idCategoria}")
    public String actualizar(@PathVariable Integer idCategoria, @Valid @RequestBody Categoria categoria) {
        Optional<Categoria> existente = service.idCategoria(idCategoria);
        if (existente.isPresent()) {
            service.actualizarCategoria(idCategoria, categoria);
            return "Categoria Actualizada Correctamente";
        } else {
            return "Categoria no encontrada con id: " + idCategoria;
        }
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente a partir de su ID", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría")
    })
    @DeleteMapping("/eliminar/{idCategoria}")
    public String eliminarCategoria(@PathVariable Integer idCategoria) {
        Optional<Categoria> existente = service.idCategoria(idCategoria);
        if (existente.isPresent()) {
            service.eliminarCategoria(idCategoria);
            return "Categoria eliminada correctamente";
        } else {
            return "Categoria no encontrada con id: " + idCategoria;
        }
    }

    @Operation(summary = "Obtener listado DTO de categorías", description = "Obtiene una vista resumida de categorías", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente")
    })
    @GetMapping("/listar-dto")
    public List<CategoriaListadoDTO> listarDTO() {
        return service.listarDTO();
    }

    @Operation(summary = "Obtener detalle simple DTO", description = "Obtiene una categoría con los nombres de sus productos", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle simple obtenido correctamente")
    })
    @GetMapping("/{id}/detalle-simple")
    public CategoriaSimpleDTO obtenerDetalleSimple(@PathVariable("id") Integer idCategoria) {
        return service.obtenerDetalleSimple(idCategoria);
    }

    @Operation(summary = "Obtener detalle completo DTO", description = "Obtiene una categoría con el detalle completo de sus productos", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle completo obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la categoría")
    })
    @GetMapping("/{id}/detalle-completo")
    public ResponseEntity<CategoriaDetalleDTO> obtenerDetalleCompleto(@PathVariable("id") Integer idCategoria) {
        CategoriaDetalleDTO dto = service.obtenerCategoriaConProductos(idCategoria);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
