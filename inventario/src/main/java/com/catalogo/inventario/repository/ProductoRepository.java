package com.catalogo.inventario.repository;

import com.catalogo.inventario.model.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    Optional<Producto> findById_Producto(Integer id_producto);

    List<Producto> findByNombre_ProductoIgnoreCase(String nombre_producto);

    List<Producto> findByPrecio_Producto(Integer precio_producto);

    List<Producto> findByStock_Producto(Integer stock_producto);

    List<Producto> findByCategoria_Id_Categoria(Integer id_categoria);

}
