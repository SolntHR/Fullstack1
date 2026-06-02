package com.resena.resena.repository;
import com.resena.resena.model.Resena;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ResenaRepository extends JpaRepository<Resena,Integer>{
    Optional<Resena> findByIdResena(Integer idResena);

    List<Resena> findByIdUsuario(Integer idUsuario);

    List<Resena> findByIdProducto(Integer idProducto);

    boolean existsByIdUsuarioAndIdProducto(Long idUsuario, Integer idProducto);

}