package com.catalogo.inventario.repository;

import com.catalogo.inventario.model.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    Optional<Producto> findById_producto(Integer id_producto);

    List<Producto> findByNombre_producto(String nombre_producto);

    List<Producto> findByPrecio_producto(Integer precio_producto);

    List<Producto> findByStock_producto(Integer stock_producto);

}
