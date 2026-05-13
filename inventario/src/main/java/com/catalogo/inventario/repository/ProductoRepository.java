package com.catalogo.inventario.repository;

import com.catalogo.inventario.model.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    Optional<Producto> findById(Integer id);

    List<Producto> findByNombreProductoIgnoreCase(String nombreProducto);

    List<Producto> findByPrecio(Integer precio);

    List<Producto> findByStock(Integer stock);

    List<Producto> findByCategoriaIdCategoria(Integer idcategoria);

}
