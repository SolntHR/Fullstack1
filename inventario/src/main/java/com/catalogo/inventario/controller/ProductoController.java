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

    @GetMapping("/productoI/{id}")
    public Optional<Producto> buscarPorId(@PathVariable Integer id){
        return service.buscarPorId(id);
    }

    @GetMapping("/productoN/{nombre}")
    public List<Producto> buscarPorNombre(@PathVariable String nombre){
        return service.buscarPorNombre(nombre);
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
    public String actualizar(@PathVariable Integer id, @RequestBody Producto producto){
        Optional<Producto> existente = service.buscarPorId(id);
        if(existente.isPresent()){
            service.productoUpdate(id, producto);
            return "Producto actualizado correctamente";
        }else {
            return "Producto no encontrado por id: " + id;
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        Optional<Producto> pelicula = service.buscarPorId(id);
        if(pelicula.isPresent()){
            service.eliminar(id);
            return "Producto eliminado correctamente";
        }else {
            return "Producto no encontrado con id: " + id;
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
        @PathVariable Integer idproducto,
        @PathVariable Integer cantidad) {

    Optional<Producto> productoOpt = service.buscarPorId(idproducto);

    if (productoOpt.isPresent()) {
        Producto producto = productoOpt.get();
        if (producto.getStock() >= cantidad) {
            producto.setStock(producto.getStock() - cantidad);
            
            service.productoUpdate(idproducto, producto);
            
            return ResponseEntity.ok("Stock actualizado. Nuevo stock: " + producto.getStock());
        } else {
            return ResponseEntity.badRequest().body("Stock insuficiente para la cantidad solicitada.");
        }
    } else {
        return ResponseEntity.status(404).body("Producto no encontrado con id: " + idproducto);
    }
    }
}