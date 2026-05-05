package com.catalogo.inventario.repository;

import com.catalogo.inventario.model.Categoria;

//import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    Optional<Categoria> findById_categoria(Integer id_categoria);

    Optional<Categoria> findByNombre_categoria(String nombre_categoria);
    
}