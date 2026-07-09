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

   
    public Optional<Producto> buscarPorId(Integer idProducto) {
        return repository.findById(idProducto);
    }

   
    public List<Producto> buscarPorNombre(String nombreProducto) {
        return repository.findByNombreProductoIgnoreCase(nombreProducto);
    }


    public List<Producto> buscarPorCategoria(Integer idCategoria) {
        return repository.findByCategoriaIdCategoria(idCategoria);
    }


    public Producto agregar(Producto producto) {
        return repository.save(producto);
    }


    public Optional<Producto> productoUpdate(Integer idProducto, Producto productoActualizado) {
        return repository.findById(idProducto).map(productoExistente -> {
            productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
            productoExistente.setPrecioProducto(productoActualizado.getPrecioProducto());
            productoExistente.setStockProducto(productoActualizado.getStockProducto());
            productoExistente.setCategoria(productoActualizado.getCategoria()); // FK
            return repository.save(productoExistente);
        });
    }

    
    public boolean eliminar(Integer idProducto) {
        if (repository.existsById(idProducto)) {
            repository.deleteById(idProducto);
            return true;
        }
        return false;
    }


    public List<ProductoListadoDTO> listarProductoDTO(){
        List<Producto> productos = repository.findAll();
        List<ProductoListadoDTO> listaDTO = new ArrayList<>();
        
        for(Producto p : productos){
            ProductoListadoDTO dto = new ProductoListadoDTO();
            dto.setIdProducto(p.getIdProducto());
            dto.setNombreProducto(p.getNombreProducto());
            dto.setPrecioProducto(p.getPrecioProducto());
            dto.setStockProducto(p.getStockProducto());

            if (p.getCategoria() != null) {
                dto.setNombreCategoria(p.getCategoria().getNombreCategoria());
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
            dto.setIdProducto(p.getIdProducto());
            dto.setNombreProducto(p.getNombreProducto());

            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public ProductoDetalleDTO obtenerDetalleProducto(Integer idProducto){
        Producto p = repository.findById(idProducto).orElse(null);
        if(p == null){
            return null;
        }
        ProductoDetalleDTO dto = new ProductoDetalleDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombreProducto(p.getNombreProducto());
        dto.setDescripcionProducto(p.getDescripcionProducto());
        dto.setPrecioProducto(p.getPrecioProducto());
        dto.setStockProducto(p.getStockProducto());

        if(p.getCategoria() != null){
            CategoriaListadoDTO catDTO = new CategoriaListadoDTO();
            catDTO.setIdCategoria(p.getCategoria().getIdCategoria());
            catDTO.setNombreCategoria(p.getCategoria().getNombreCategoria());
            dto.setCategoria(catDTO);
        }
        return dto;
    }

    

}