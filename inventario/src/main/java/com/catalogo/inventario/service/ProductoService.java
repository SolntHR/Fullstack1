package com.catalogo.inventario.service;

import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    // GET: listar todos los productos
    public List<Producto> findAll() {
        return repository.findAll();
    }

    // GET: buscar por id
    public Optional<Producto> findById(Integer id) {
        return repository.findById(id);
    }

    // GET: buscar por nombre
    public List<Producto> findByName(String nombre_producto) {
        return repository.findByNombre_ProductoIgnoreCase(nombre_producto);
    }

    // GET: buscar por categoria (objeto Categoria completo)
    public List<Producto> findByCategory(Integer id_Categoria) {
        return repository.findByCategoria_Id_Categoria(id_Categoria);
    }

    // POST: agregar producto
    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    // PUT: actualizar producto
    public Optional<Producto> update(Integer id, Producto productoActualizado) {
        return repository.findById(id).map(productoExistente -> {
            productoExistente.setNombre_producto(productoActualizado.getNombre_producto());
            productoExistente.setPrecio_producto(productoActualizado.getPrecio_producto());
            productoExistente.setStock_producto(productoActualizado.getStock_producto());
            productoExistente.setCategoria(productoActualizado.getCategoria()); // FK
            return repository.save(productoExistente);
        });
    }

    // DELETE: eliminar producto
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}