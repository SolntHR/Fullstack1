package com.catalogo.inventario.service;

import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    // GET: listar todas las categorias
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    // GET: buscar por id
    public Optional<Categoria> findById(Integer id) {
        return repository.findById(id);
    }

    // GET: buscar por nombre
    public Optional<Categoria> findByName(String nombre) {
        return repository.findByNombre_CategoriaIgnoreCase(nombre);
    }

    // POST: agregar categoria
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    // PUT: actualizar categoria
    public Optional<Categoria> update(Integer id, Categoria categoriaActualizada) {
        return repository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNombre_categoria(categoriaActualizada.getNombre_categoria());
            return repository.save(categoriaExistente);
        });
    }

    // DELETE: eliminar categoria
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
