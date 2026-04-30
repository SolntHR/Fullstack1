package com.catalogo.inventario.model;

import jakarta.persistence.*;               // Permite mapear la clase a una tabla en la base de atos
import lombok.AllArgsConstructor;           //Genera automaticamente un construcotr con todos los atributos 
import lombok.Data;                         //Genera getters, setters, toString, equals, y haschCode autoamticamente
import lombok.NoArgsConstructor;            //Genera un constructor vacio sin parametros
import jakarta.validation.constraints.*;    // permite validar los datos (Ej: campos obligatorios valores minimos, etc.)

@Entity                                     // Indica que esta clase es una entidad que se mapeara a una tabla en la BD
@Table(name="categoria")                     // Define el nombre de la tabla en la base datos
@Data                                       //Genera autoamticamente getters, setters, ToString,Eqquals y hashCode
@NoArgsConstructor                          //Genera un constructor vacio necesario para JPA
@AllArgsConstructor

public class Categoria {

    //IDCATEGORIA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_categoria;
    //NOMBRE_CATEGORIA
    @NotBlank(message = "El nombre del producto no puede esta vacio")
    @Column(nullable = false, length = 40)
    private String nombre_categoria;
}
