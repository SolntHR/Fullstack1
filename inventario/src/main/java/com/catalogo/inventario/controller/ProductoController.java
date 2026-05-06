package com.catalogo.inventario.controller;

import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/producto")
public class ProductoController {
    
    @Autowired
    private ProductoService service;

    //Get: se listan los productos
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

    @GetMapping("/categoria/{id}")
    public List<Producto> buscarPorCategoria(@PathVariable Integer id_Categoria){
        return service.buscarPorCategoria(id_Categoria);
    }

    @PostMapping
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
}
