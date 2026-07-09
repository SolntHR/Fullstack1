package com.catalogo.inventario.controller;

import com.catalogo.inventario.dto.ProductoDetalleDTO;
import com.catalogo.inventario.dto.ProductoListadoDTO;
import com.catalogo.inventario.dto.ProductoSimpleDTO;
import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventario/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Operation(summary = "Listar todos los productos", description = "Obtiene la lista completa de productos registrados", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    })
    @GetMapping("/listar")
    public List<Producto> listar() {
        return service.listaProductos();
    }

    @Operation(summary = "Buscar producto por ID", description = "Obtiene un producto específico a partir de su identificador", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    @GetMapping("/productoI/{idProducto}")
    public Optional<Producto> buscarPorId(@PathVariable Integer idProducto) {
        return service.buscarPorId(idProducto);
    }

    @Operation(summary = "Buscar productos por nombre", description = "Obtiene los productos que coinciden con el nombre indicado", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente")
    })
    @GetMapping("/productoN/{nombre}")
    public List<Producto> buscarPorNombre(@PathVariable("nombre") String nombreProducto) {
        return service.buscarPorNombre(nombreProducto);
    }

    @Operation(summary = "Buscar productos por categoría", description = "Obtiene todos los productos asociados a una categoría", tags = {"1. Consultas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos de la categoría obtenidos correctamente")
    })
    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> buscarPorCategoria(@PathVariable Integer idCategoria) {
        return service.buscarPorCategoria(idCategoria);
    }

    @Operation(summary = "Agregar un producto", description = "Crea un nuevo producto en el inventario", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping("/agregar-producto")
    public Producto crear(@RequestBody Producto producto) {
        return service.agregar(producto);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    @PutMapping("/actualizar/{id}")
    public String actualizar(@PathVariable("id") Integer idProducto, @RequestBody Producto producto) {
        Optional<Producto> existente = service.buscarPorId(idProducto);
        if (existente.isPresent()) {
            service.productoUpdate(idProducto, producto);
            return "Producto actualizado correctamente";
        } else {
            return "Producto no encontrado por id: " + idProducto;
        }
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente a partir de su ID", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    @DeleteMapping("/eliminar/{idProducto}")
    public String eliminar(@PathVariable Integer idProducto) {
        Optional<Producto> producto = service.buscarPorId(idProducto);
        if (producto.isPresent()) {
            service.eliminar(idProducto);
            return "Producto eliminado correctamente";
        } else {
            return "Producto no encontrado con id: " + idProducto;
        }
    }

    @Operation(summary = "Descontar stock", description = "Reduce el stock de un producto en la cantidad indicada", tags = {"2. Gestión"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    @PutMapping("/{idProducto}/descontar/{cantidad}")
    public ResponseEntity<?> descontar(
            @PathVariable Integer idProducto,
            @PathVariable Integer cantidad) {

        Optional<Producto> productoOpt = service.buscarPorId(idProducto);

        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (producto.getStockProducto() >= cantidad) {
                producto.setStockProducto(producto.getStockProducto() - cantidad);
                service.productoUpdate(idProducto, producto);
                return ResponseEntity.ok("Stock actualizado. Nuevo stock: " + producto.getStockProducto());
            } else {
                return ResponseEntity.badRequest().body("Stock insuficiente para la cantidad solicitada.");
            }
        } else {
            return ResponseEntity.status(404).body("Producto no encontrado con id: " + idProducto);
        }
    }

    @Operation(summary = "Obtener listado DTO", description = "Obtiene una vista resumida de productos con información relevante", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado DTO obtenido correctamente")
    })
    @GetMapping("/listado-dto")
    public List<ProductoListadoDTO> obtenerListado() {
        return service.listarProductoDTO();
    }

    @Operation(summary = "Obtener listado simple DTO", description = "Obtiene una vista simple de productos", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado simple obtenido correctamente")
    })
    @GetMapping("/simple-dto")
    public List<ProductoSimpleDTO> obtenerSimple() {
        return service.listarProductoSimpleDTO();
    }

    @Operation(summary = "Obtener detalle DTO", description = "Obtiene el detalle completo de un producto en formato DTO", tags = {"3. DTOs"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle del producto obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    @GetMapping("/{idProducto}/detalle")
    public ResponseEntity<ProductoDetalleDTO> obtenerDetalle(@PathVariable Integer idProducto) {
        ProductoDetalleDTO dto = service.obtenerDetalleProducto(idProducto);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
