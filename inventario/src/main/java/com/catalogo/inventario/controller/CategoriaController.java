package com.catalogo.inventario.controller;

import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.catalogo.inventario.dto.CategoriaDetalleDTO;
import com.catalogo.inventario.dto.CategoriaListadoDTO;
import com.catalogo.inventario.dto.CategoriaSimpleDTO;

@RestController
@RequestMapping("/inventario/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;
    
    @GetMapping("/listarcategoria")
    public List<Categoria> listarCategoria(){
        return service.listarCategoria();
    }
 
    @GetMapping("/categoriaI/{idCategoria}")
    public Optional<Categoria> buscarPorId(@PathVariable Integer idCategoria){
        return service.idCategoria(idCategoria);
    }

    @GetMapping("/categoriaN/{nombreCategoria}")
    public Optional <Categoria> buscarPorNombre(@PathVariable String nombreCategoria){
        return service.nombreCategoria(nombreCategoria);
    }
    
    @PostMapping("/agregar-categoria")
    public Categoria crear(@RequestBody Categoria categoria){
        return service.guardarCategoria(categoria);
    }

    @PutMapping("/actualizar-categoria/{idCategoria}")
    public String actualizar(@Valid @PathVariable Integer idCategoria, @RequestBody Categoria categoria){
        Optional<Categoria> existente = service.idCategoria(idCategoria);
        if(existente.isPresent()){
            service.actualizarCategoria(idCategoria, categoria);
            return "Categoria Actualizada Correctamente";
        }else {
            return "Categoria no encontrada con id: "+idCategoria;
        }
    }

    @DeleteMapping("/eliminar/{idCategoria}")
    public String eliminarCategoria(@PathVariable Integer idCategoria){
        Optional<Categoria> existente = service.idCategoria(idCategoria);
        if(existente.isPresent()){
            service.eliminarCategoria(idCategoria);
            return "Categoria eliminada correctamente";
        }else{
            return "Categoria no encontrada con id: " + idCategoria;
        }
    }


    @GetMapping("/listar-dto")
    public List<CategoriaListadoDTO> listarDTO(){
        return service.listarDTO();
    }

    @GetMapping("/{id}/detalle-simple")
    public CategoriaSimpleDTO obtenerDetalleSimple(@PathVariable Integer idCategoria){
        return service.obtenerDetalleSimple(idCategoria);
    }

    @GetMapping("/{id}/detalle-completo")
    public ResponseEntity<CategoriaDetalleDTO> obtenerDetalleCompleto(@PathVariable Integer idCategoria){
        CategoriaDetalleDTO dto = service.obtenerCategoriaConProductos(idCategoria);
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
