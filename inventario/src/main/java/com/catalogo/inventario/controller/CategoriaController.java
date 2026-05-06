package com.catalogo.inventario.controller;

import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController

@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;
    // Se inyecta el servicio

    //Get: listar todas las categorias
    @GetMapping("/listarcategoria")
    public List<Categoria> listarCategoria(){
        return service.listarCategoria();
    }
    //Get: Buscar por id
    @GetMapping("/categoriaI/{id}")
    public Optional<Categoria> buscarPorId(@PathVariable Integer id){
        return service.idCategoria(id);
    }
    //Get: Buscar por nombre
    @GetMapping("/categoriaN/{nombre}")
    public Optional <Categoria> buscarPorNombre(@PathVariable String nombre){
        return service.nombreCategoria(nombre);
    }
    
    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria){
        return service.guardarCategoria(categoria);
    }

    @PutMapping("/actualizar-categoria/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Categoria categoria){
        Optional<Categoria> existente = service.idCategoria(id);
        if(existente.isPresent()){
            service.actualizarCategoria(id, categoria);
            return "Categoria Actualizada Correctamente";
        }else {
            return "Categoria no encontrada con id: "+id;
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id){
        Optional<Categoria> existente = service.idCategoria(id);
        if(existente.isPresent()){
            service.eliminarCategoria(id);
            return "Categoria eliminada correctamente";
        }else{
            return "Categoria no encontrada con id: " + id;
        }
    }
}
