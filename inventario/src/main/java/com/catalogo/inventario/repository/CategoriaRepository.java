package com.catalogo.inventario.repository;

import com.catalogo.inventario.model.Categoria;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    Optional<Categoria> findByIdCategoria(Integer idCategoria);

    Optional<Categoria> findByNombreCategoriaIgnoreCase(String nombreCategoria);
    
}