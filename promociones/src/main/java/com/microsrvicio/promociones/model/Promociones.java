package com.microsrvicio.promociones.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;               // Permite mapear la clase a una tabla en la base de atos
import lombok.AllArgsConstructor;           //Genera automaticamente un construcotr con todos los atributos 
import lombok.Data;                         //Genera getters, setters, toString, equals, y haschCode autoamticamente
import lombok.NoArgsConstructor;            //Genera un constructor vacio sin parametros
import jakarta.validation.constraints.*;    // permite validar los datos (Ej: campos obligatorios valores minimos, etc.)

@Entity                                     // Indica que esta clase es una entidad que se mapeara a una tabla en la BD
@Table(name="Promociones")                     // Define el nombre de la tabla en la base datos
@Data                                       //Genera autoamticamente getters, setters, ToString,Eqquals y hashCode
@NoArgsConstructor                          //Genera un constructor vacio necesario para JPA
@AllArgsConstructor

public class Promociones {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromocion;

    // NOMBRE
    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombrePromocion;

    // CODIGO PROMOCIONAL
    @NotBlank(message = "El codigo promocional no puede estar vacio")
    @Column(name = "codigo_promocional", length = 10, nullable = false, unique = true)
    private String codigoPromocional;

    // DESCUENTO
    @NotNull(message = "El descuento no puede ser nulo")
    @Column(name = "descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal descuento;
    
    // FECHA DE INICIO
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    // FECHA DE FIN
    @NotNull(message = "La fecha de fin no puede ser nula")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    // VECES DE USO
    @NotNull(message = "Las veces de uso no pueden ser nulas")
    @Column(name = "veces_uso", nullable = false)
    private Integer vecesUso;

    // MONTO MINIMO
    @NotNull(message = "El monto minimo no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = false, message = "El monto minimo debe ser mayor a 0")  
    @Column(name = "monto_minimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMinimo;

}