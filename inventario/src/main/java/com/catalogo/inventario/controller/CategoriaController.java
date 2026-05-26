package com.catalogo.inventario.controller;

import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.catalogo.inventario.dto.CategoriaDetalleDTO;
import com.catalogo.inventario.dto.CategoriaListadoDTO;
import com.catalogo.inventario.dto.CategoriaSimpleDTO;

@RestController

@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;
    
    @GetMapping("/listarcategoria")
    public List<Categoria> listarCategoria(){
        return service.listarCategoria();
    }
 
    @GetMapping("/categoriaI/{id}")
    public Optional<Categoria> buscarPorId(@PathVariable Integer id){
        return service.idCategoria(id);
    }

    @GetMapping("/categoriaN/{nombre}")
    public Optional <Categoria> buscarPorNombre(@PathVariable String nombre){
        return service.nombreCategoria(nombre);
    }
    
    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria){
        return service.guardarCategoria(categoria);
    }

    @PutMapping("/actualizar-categoria/{id}")
    public String actualizar(@Valid @PathVariable Integer id, @RequestBody Categoria categoria){
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


    @GetMapping("/listar-dto")
    public List<CategoriaListadoDTO> listarDTO(){
        return service.listarDTO();
    }

    @GetMapping("/{id}/detalle-simple")
    public CategoriaSimpleDTO obtenerDetalleSimple(@PathVariable Integer id){
        return service.obtenerDetalleSimple(id);
    }

    @GetMapping("/{id}/detalle-completo")
    public ResponseEntity<CategoriaDetalleDTO> obtenerDetalleCompleto(@PathVariable Integer id){
        CategoriaDetalleDTO dto = service.obtenerCategoriaConProductos(id);
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
