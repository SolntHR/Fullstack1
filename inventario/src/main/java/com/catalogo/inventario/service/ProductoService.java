package com.catalogo.inventario.service;

import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogo.inventario.dto.CategoriaListadoDTO;
import com.catalogo.inventario.dto.ProductoDetalleDTO;
import com.catalogo.inventario.dto.ProductoSimpleDTO;
import com.catalogo.inventario.dto.ProductoListadoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;


    public List<Producto> listaProductos() {
        return repository.findAll();
    }

   
    public Optional<Producto> buscarPorId(Integer id) {
        return repository.findById(id);
    }

   
    public List<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreProductoIgnoreCase(nombre);
    }

  
    public List<Producto> buscarPorCategoria(Integer idCategoria) {
        return repository.findByCategoriaIdCategoria(idCategoria);
    }

   
    public Producto agregar(Producto producto) {
        return repository.save(producto);
    }

   
    public Optional<Producto> productoUpdate(Integer id, Producto productoActualizado) {
        return repository.findById(id).map(productoExistente -> {
            productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setCategoria(productoActualizado.getCategoria()); // FK
            return repository.save(productoExistente);
        });
    }

    
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

  
    public List<ProductoListadoDTO> listarProductoDTO(){
        List<Producto> productos = repository.findAll();
        List<ProductoListadoDTO> listaDTO = new ArrayList<>();
        
        for(Producto p : productos){
            ProductoListadoDTO dto = new ProductoListadoDTO();
            dto.setIdproducto(p.getIdproducto());
            dto.setNombreProducto(p.getNombreProducto());
            dto.setPrecio_producto(p.getPrecio());
            dto.setStock_producto(p.getStock());

            if (p.getCategoria() != null) {
                dto.setNombre_categoria(p.getCategoria().getNombre());
            }
            listaDTO.add(dto);
        }
        return listaDTO;
    }
  
    public List<ProductoSimpleDTO> listarProductoSimpleDTO(){
        List<Producto> productos = repository.findAll();
        List<ProductoSimpleDTO> listaDTO = new ArrayList<>();

        for(Producto p : productos){
            ProductoSimpleDTO dto = new ProductoSimpleDTO();
            dto.setIdproducto(p.getIdproducto());
            dto.setNombreProducto(p.getNombreProducto());

            listaDTO.add(dto);
        }
        return listaDTO;
    }
  
    public ProductoDetalleDTO obtenerDetalleProducto(Integer id){
        Producto p = repository.findById(id).orElse(null);
        if(p == null){
            return null;
        }
        ProductoDetalleDTO dto = new ProductoDetalleDTO();
        dto.setIdproducto(p.getIdproducto());
        dto.setNombreProducto(p.getNombreProducto());
        dto.setDescripcion_producto(p.getDescripcion_producto());
        dto.setPrecio_producto(p.getPrecio());
        dto.setStock_producto(p.getStock());

        if(p.getCategoria() != null){
            CategoriaListadoDTO catDTO = new CategoriaListadoDTO();
            catDTO.setIdcategoria(p.getCategoria().getIdCategoria());
            catDTO.setNombre_categoria(p.getCategoria().getNombre());
            dto.setCategoria(catDTO);
        }
        return dto;
    }

    

}