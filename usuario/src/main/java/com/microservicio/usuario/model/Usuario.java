package com.microservicio.usuario.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;               // Permite mapear la clase a una tabla en la base de atos
import lombok.AllArgsConstructor;           //Genera automaticamente un construcotr con todos los atributos 
import lombok.Data;                         //Genera getters, setters, toString, equals, y haschCode autoamticamente
import lombok.NoArgsConstructor;            //Genera un constructor vacio sin parametros
import jakarta.validation.constraints.*;    // permite validar los datos (Ej: campos obligatorios valores minimos, etc.)

@Entity                                     // Indica que esta clase es una entidad que se mapeara a una tabla en la BD
@Table(name="usuario")                     // Define el nombre de la tabla en la base datos
@Data                                       //Genera autoamticamente getters, setters, ToString,Eqquals y hashCode
@NoArgsConstructor                          //Genera un constructor vacio necesario para JPA
@AllArgsConstructor

public class Usuario {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    // NOMBRE
    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    // APELLIDO
    @NotBlank(message = "El apellido no puede estar vacio")
    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;

    // EMAIL
    @NotBlank(message = "El email no puede estar vacio")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // PASSWORD

    // NUEVO CAMPO: contrseña del usuario
    // Necesario para poder aunteticar (login)
    // Permite recibir la contraseña en requests (POST, PUT)
    //pero evita que se desenvuelva en respuestas JSON  por seguridad
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Column(name = "password", nullable = false, length = 255) // Nota: El largo 255 es ideal porque al encriptar la clave con BCrypt, el texto se vuelve largo.
    private String password;

    // ROL
    @ManyToOne(fetch = FetchType.EAGER) // Indica que un usuario puede tener un rol, y se cargará de forma inmediata al obtener el usuario
    @JoinColumn(name = "idRol", nullable = false) // Define la columna de la FK
    private Rol rol;

    
}
