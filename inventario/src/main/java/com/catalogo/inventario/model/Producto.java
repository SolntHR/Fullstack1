package com.catalogo.inventario.model;

import jakarta.persistence.*;               // Permite mapear la clase a una tabla en la base de atos
import lombok.AllArgsConstructor;           //Genera automaticamente un construcotr con todos los atributos 
import lombok.Data;                         //Genera getters, setters, toString, equals, y haschCode autoamticamente
import lombok.NoArgsConstructor;            //Genera un constructor vacio sin parametros
import jakarta.validation.constraints.*;    // permite validar los datos (Ej: campos obligatorios valores minimos, etc.)

@Entity                                     // Indica que esta clase es una entidad que se mapeara a una tabla en la BD
@Table(name="producto")                     // Define el nombre de la tabla en la base datos
@Data                                       //Genera autoamticamente getters, setters, ToString,Eqquals y hashCode
@NoArgsConstructor                          //Genera un constructor vacio necesario para JPA
@AllArgsConstructor

// RECUERDEN PRIMERO HACER LA BASE DE DATOS EN LARAGON: CREATE DATABASE inventario;
public class Producto {
    
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_producto;

    //NOMBRE
    @NotBlank(message = "El nombre del producto no puede esta vacio")
    @Column(nullable = false, length = 100)
    private String nombre_producto;
    //DESCRIPCION
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Column(nullable = false, length = 100)
    private String descripcion_producto;
    //PRECIO
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    @NotNull(message = "Debe ingresar un precio")
    private Integer precio_producto;
    //STOCK
    @Min(value = 0, message = "El stock debe ser mayor a 0")
    @NotNull(message = "Debe ingresar la cantidad de stock")
    private Integer stock_producto;

}
