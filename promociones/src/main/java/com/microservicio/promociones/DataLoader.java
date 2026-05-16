package com.microservicio.promociones;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import com.microservicio.promociones.model.Promociones;
import com.microservicio.promociones.repository.PromocionesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(PromocionesRepository repository) {
        return args -> {
            
            // Cargar cada promoción validando de manera individual por su código único
            crearPromocionSiNoExiste(repository, 
                "Descuento de Verano", "VERANO2024", 
                "2024-06-01", "2024-08-31", "50.00", 100, "10.00");

            crearPromocionSiNoExiste(repository, 
                "Descuento de Invierno", "INVIERNO2024", 
                "2024-12-01", "2025-02-28", "30.00", 50, "15.00");

            crearPromocionSiNoExiste(repository, 
                "Descuento Gamer", "GAMER15", 
                "2026-05-16", "2026-06-30", "5000.00", 0, "15.00");

            crearPromocionSiNoExiste(repository, 
                "Promo Navidad", "NAVIDAD2025", 
                "2026-05-16", "2026-07-15", "12000.00", 0, "50.00");

            crearPromocionSiNoExiste(repository, 
                "Cyber Descuento", "CYBER30", 
                "2026-05-25", "2026-05-28", "15000.00", 0, "30.00");
        };
    }

    private void crearPromocionSiNoExiste(PromocionesRepository repo, 
                                          String nombre, 
                                          String codigo, 
                                          String fechaInicio, 
                                          String fechaFin, 
                                          String montoMin, 
                                          int vecesUso, 
                                          String descuento) {
                                              
        // Buscamos si ya existe por el código promocional único
        Optional<Promociones> promoOpt = repo.findByCodigoPromocional(codigo);
        
        if (promoOpt.isEmpty()) {
            Promociones nuevaPromo = new Promociones();
            nuevaPromo.setNombrePromocion(nombre);
            nuevaPromo.setCodigoPromocional(codigo);
            nuevaPromo.setFechaInicio(LocalDate.parse(fechaInicio));
            nuevaPromo.setFechaFin(LocalDate.parse(fechaFin));
            nuevaPromo.setMontoMinimo(new BigDecimal(montoMin));
            nuevaPromo.setVecesUso(vecesUso);
            nuevaPromo.setDescuento(new BigDecimal(descuento));
            
            repo.save(nuevaPromo);
            System.out.println("Cargada promoción: " + nombre + " [" + codigo + "]");
        } else {
            System.out.println("La promoción con código " + codigo + " ya existe. Saltando...");
        }
    }
}