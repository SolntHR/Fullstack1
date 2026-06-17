package com.catalogo.inventario.controller;

import com.catalogo.inventario.dto.ProductoDetalleDTO;
import com.catalogo.inventario.dto.ProductoListadoDTO;
import com.catalogo.inventario.dto.ProductoSimpleDTO;
import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.service.ProductoService;
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

    @GetMapping("/listar")
    public List<Producto> listar(){
        return service.listaProductos();
    }

    @GetMapping("/productoI/{idProducto}")
    public Optional<Producto> buscarPorId(@PathVariable Integer idProducto){
        return service.buscarPorId(idProducto);
    }

    @GetMapping("/productoN/{nombre}")
    public List<Producto> buscarPorNombre(@PathVariable String nombreProducto){
        return service.buscarPorNombre(nombreProducto);
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> buscarPorCategoria(@PathVariable Integer idCategoria){
        return service.buscarPorCategoria(idCategoria);
    }

    @PostMapping("/agregar-producto")
    public Producto crear(@RequestBody Producto producto){
        return service.agregar(producto);
    }

    @PutMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer idProducto, @RequestBody Producto producto){
        Optional<Producto> existente = service.buscarPorId(idProducto);
        if(existente.isPresent()){
            service.productoUpdate(idProducto, producto);
            return "Producto actualizado correctamente";
        }else {
            return "Producto no encontrado por id: " + idProducto;
        }
    }

    @DeleteMapping("/eliminar/{idProducto}")
    public String eliminar(@PathVariable Integer idProducto){
        Optional<Producto> pelicula = service.buscarPorId(idProducto);
        if(pelicula.isPresent()){
            service.eliminar(idProducto);
            return "Producto eliminado correctamente";
        }else {
            return "Producto no encontrado con id: " + idProducto;
        }
    }


    @GetMapping("/listado-dto")
    public List<ProductoListadoDTO> obtenerListado(){
        return service.listarProductoDTO();
    }
    
    @GetMapping("/simple-dto")
    public List<ProductoSimpleDTO> obtenerSimple(){
        return service.listarProductoSimpleDTO();
    }

    @GetMapping("/{idProducto}/detalle")
    public ResponseEntity<ProductoDetalleDTO> obtenerDetalle(@PathVariable Integer idProducto){
        ProductoDetalleDTO  dto = service.obtenerDetalleProducto(idProducto);
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

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
}