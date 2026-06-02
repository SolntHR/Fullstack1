package com.catalogo.inventario.service;

import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.repository.CategoriaRepository;
import com.catalogo.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogo.inventario.dto.CategoriaDetalleDTO;
import com.catalogo.inventario.dto.CategoriaListadoDTO;
import com.catalogo.inventario.dto.CategoriaSimpleDTO;
import com.catalogo.inventario.dto.ProductoDetalleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ProductoRepository productoRepository;


    public List<Categoria> listarCategoria() {
        return repository.findAll();
    }


    public Optional<Categoria> idCategoria(Integer id) {
        return repository.findById(id);
    }


    public Optional<Categoria> nombreCategoria(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }


    public Categoria guardarCategoria(Categoria categoria) {
        return repository.save(categoria);
    }


    public Optional<Categoria> actualizarCategoria(Integer id, Categoria categoriaActualizada) {
        return repository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNombre(categoriaActualizada.getNombre());
            return repository.save(categoriaExistente);
        });
    }


    public boolean eliminarCategoria(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<CategoriaListadoDTO> listarDTO(){

        List<Categoria> categoria = repository.findAll();
        List<CategoriaListadoDTO> lista = new ArrayList<>();

        for(Categoria c : categoria){
            CategoriaListadoDTO dto = new CategoriaListadoDTO();
            dto.setIdcategoria(c.getIdCategoria());
            dto.setNombre_categoria(c.getNombre());

            lista.add(dto);
        }
        return lista;
    }

    public CategoriaSimpleDTO obtenerDetalleSimple(Integer id){
        Optional<Categoria> categoriaOpt = repository.findById(id);

        if(categoriaOpt.isEmpty()){
            return null;
        }

        Categoria categoria = categoriaOpt.get();
        List<Producto> productos = productoRepository.findByCategoriaIdCategoria(id);

        List<String> nombre_producto = new ArrayList<>();

        for(Producto p : productos){
            nombre_producto.add(p.getNombreProducto());
        }

        CategoriaSimpleDTO dto = new CategoriaSimpleDTO();
        dto.setIdcategoria(categoria.getIdCategoria());
        dto.setNombre_categoria(categoria.getNombre());
        dto.setProducto(nombre_producto);

        return dto;
    }

    public CategoriaDetalleDTO obtenerCategoriaConProductos(Integer id){
        Categoria cat = repository.findById(id).orElse(null);
        if(cat == null) return null;

        CategoriaDetalleDTO dto = new CategoriaDetalleDTO();
        dto.setIdcategoria(cat.getIdCategoria());
        dto.setNombre_categoria(cat.getNombre());

        List<Producto> productoEnti = productoRepository.findByCategoriaIdCategoria(id);
        List<ProductoDetalleDTO> listaProductosDTO = new ArrayList<>();

        for(Producto p : productoEnti){
            ProductoDetalleDTO pDTO = new ProductoDetalleDTO();
            pDTO.setIdProducto(p.getIdProducto());
            pDTO.setNombreProducto(p.getNombreProducto());
            pDTO.setDescripcion_producto(p.getDescripcion_producto());
            pDTO.setPrecio_producto(p.getPrecio());
            pDTO.setStock_producto(p.getStock());

            listaProductosDTO.add(pDTO);
        }
        dto.setProductos(listaProductosDTO);
        return dto;
    }
}
