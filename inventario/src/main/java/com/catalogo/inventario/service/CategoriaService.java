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


    public Optional<Categoria> idCategoria(Integer idCategoria) {
        return repository.findByIdCategoria(idCategoria);
    }


    public Optional<Categoria> nombreCategoria(String nombreCategoria) {
        return repository.findByNombreCategoriaIgnoreCase(nombreCategoria);
    }


    public Categoria guardarCategoria(Categoria categoria) {
        return repository.save(categoria);
    }


    public Optional<Categoria> actualizarCategoria(Integer idCategoria, Categoria categoriaActualizada) {
        return repository.findById(idCategoria).map(categoriaExistente -> {
            categoriaExistente.setNombreCategoria(categoriaActualizada.getNombreCategoria());
            return repository.save(categoriaExistente);
        });
    }


    public boolean eliminarCategoria(Integer idCategoria) {
        if (repository.existsById(idCategoria)) {
            repository.deleteById(idCategoria);
            return true;
        }
        return false;
    }


    public List<CategoriaListadoDTO> listarDTO(){

        List<Categoria> categoria = repository.findAll();
        List<CategoriaListadoDTO> lista = new ArrayList<>();

        for(Categoria c : categoria){
            CategoriaListadoDTO dto = new CategoriaListadoDTO();
            dto.setIdCategoria(c.getIdCategoria());
            dto.setNombreCategoria(c.getNombreCategoria());

            lista.add(dto);
        }
        return lista;
    }

    public CategoriaSimpleDTO obtenerDetalleSimple(Integer idCategoria){
        Optional<Categoria> categoriaOpt = repository.findByIdCategoria(idCategoria);

        if(categoriaOpt.isEmpty()){
            return null;
        }

        Categoria categoria = categoriaOpt.get();
        List<Producto> productos = productoRepository.findByCategoriaIdCategoria(idCategoria);

        List<String> nombreProducto = new ArrayList<>();

        for(Producto p : productos){
            nombreProducto.add(p.getNombreProducto());
        }

        CategoriaSimpleDTO dto = new CategoriaSimpleDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setProducto(nombreProducto);

        return dto;
    }

    public CategoriaDetalleDTO obtenerCategoriaConProductos(Integer idCategoria){
        Categoria cat = repository.findByIdCategoria(idCategoria).orElse(null);
        if(cat == null) return null;

        CategoriaDetalleDTO dto = new CategoriaDetalleDTO();
        dto.setIdCategoria(cat.getIdCategoria());
        dto.setNombreCategoria(cat.getNombreCategoria());

        List<Producto> productoEnti = productoRepository.findByCategoriaIdCategoria(idCategoria);
        List<ProductoDetalleDTO> listaProductosDTO = new ArrayList<>();

        for(Producto p : productoEnti){
            ProductoDetalleDTO pDTO = new ProductoDetalleDTO();
            pDTO.setIdProducto(p.getIdProducto());
            pDTO.setNombreProducto(p.getNombreProducto());
            pDTO.setDescripcionProducto(p.getDescripcionProducto());
            pDTO.setPrecioProducto(p.getPrecioProducto());
            pDTO.setStockProducto(p.getStockProducto());

            listaProductosDTO.add(pDTO);
        }
        dto.setProductos(listaProductosDTO);
        return dto;
    }
}
