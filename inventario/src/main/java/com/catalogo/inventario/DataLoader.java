package com.catalogo.inventario;

import com.catalogo.inventario.model.Categoria;
import com.catalogo.inventario.model.Producto;
import com.catalogo.inventario.repository.CategoriaRepository;
import com.catalogo.inventario.repository.ProductoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader{

    @Bean
    CommandLineRunner initInventario(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        return args -> {

            Categoria consolas = categoriaRepository.findByNombreIgnoreCase("Consolas")
                    .orElseGet(() -> {
                        Categoria c = new Categoria();
                        c.setNombre("Consolas");
                        return categoriaRepository.save(c);
                    });

            Categoria perifericos = categoriaRepository.findByNombreIgnoreCase("Periféricos")
                    .orElseGet(() -> {
                        Categoria c = new Categoria();
                        c.setNombre("Periféricos");
                        return categoriaRepository.save(c);
                    });

            Categoria monitores = categoriaRepository.findByNombreIgnoreCase("Monitores")
                    .orElseGet(() -> {
                        Categoria c = new Categoria();
                        c.setNombre("Monitores");
                        return categoriaRepository.save(c);
                    });

            if (productoRepository.findByNombreProductoIgnoreCase("PS5").isEmpty()) {
                Producto p1 = new Producto();
                p1.setNombreProducto("PS5");
                p1.setDescripcion_producto("Consola con gran potencia");
                p1.setPrecio(399990);
                p1.setStock(15);
                p1.setCategoria(consolas);
                productoRepository.save(p1);
            }

            if (productoRepository.findByNombreProductoIgnoreCase("Mouse Inalambrico").isEmpty()) {
                Producto p2 = new Producto();
                p2.setNombreProducto("Mouse Inalambrico");
                p2.setDescripcion_producto("Mouse inalambrico ergonomico para uso diario y gaming casual");
                p2.setPrecio(14990);
                p2.setStock(20);
                p2.setCategoria(perifericos);
                productoRepository.save(p2);
            }

            if (productoRepository.findByNombreProductoIgnoreCase("Teclado Mecanico").isEmpty()) {
                Producto p3 = new Producto();
                p3.setNombreProducto("Teclado Mecanico");
                p3.setDescripcion_producto("Teclado mecanico con retroiluminacion y switches de alto rendimiento");
                p3.setPrecio(45990);
                p3.setStock(12);
                p3.setCategoria(perifericos);
                productoRepository.save(p3);
            }

            if (productoRepository.findByNombreProductoIgnoreCase("Monitor 24 Pulgadas").isEmpty()) {
                Producto p4 = new Producto();
                p4.setNombreProducto("Monitor 24 Pulgadas");
                p4.setDescripcion_producto("Monitor full hd de 24 pulgadas ideal para juego y estudio");
                p4.setPrecio(129990);
                p4.setStock(8);
                p4.setCategoria(monitores);
                productoRepository.save(p4);
            }
        };
    }
}
