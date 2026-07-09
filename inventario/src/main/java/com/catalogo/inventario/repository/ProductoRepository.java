package com.catalogo.inventario.repository;

import com.catalogo.inventario.model.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    Optional<Producto> findById(Integer idProducto);

    List<Producto> findByNombreProductoIgnoreCase(String nombreProducto);

    List<Producto> findByPrecioProducto(Integer precioProducto);

    List<Producto> findByStockProducto(Integer stockProducto);

    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);

}
