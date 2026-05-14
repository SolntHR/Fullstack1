package com.microservicio.usuario.service;

import com.microservicio.usuario.dto.UsuarioDTO.UsuarioSimpleDTO;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// necesario para encriptar la contraseña
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Para encriptar la contraseña
    private final PasswordEncoder passwordEncoder;

    // Constructor para inyectar el PasswordEncoder
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET: Lista a todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // GET: Buscar por ID
    public Optional<Usuario> buscarPorId(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario);
    }

    // GET: Buscar por nombre
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    // GET:Buscar por apellido
    public List<Usuario> buscarPorApellido(String apellido) {
        return usuarioRepository.findByApellido(apellido);
    }

    // GET: Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    // POST: Agregar un nuevo usuario
    public Usuario agregarUsuario(Usuario usuario) {
        // Encriptar la contraseña antes de guardar
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }

    // PUT: Actualizar un usuario existente
    public Optional<Usuario> actualizarUsuario(Integer idUsuario, Usuario usuarioActualizado) {
    return usuarioRepository.findByIdUsuario(idUsuario).map(usuario -> {
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setPassword(usuarioActualizado.getPassword());
        
        // Asegúrate de que el objeto rol enviado tenga el ID correcto
        usuario.setRol(usuarioActualizado.getRol());
        return usuarioRepository.save(usuario);
    });
}

    // DELETE: Eliminar un usuario por ID
    public boolean eliminarUsuario(Integer idUsuario) {
        if(usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
            return true;
        }
    return false;
    }

    public UsuarioSimpleDTO obtenerDetalleSimple(Integer idUsuario){
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdUsuario(idUsuario);

        if(usuarioOpt.isEmpty()){
            return null;
        }

        Usuario usuario = usuarioOpt.get();
        
        // Crear un DTO simple
        UsuarioSimpleDTO usuarioDTO = new UsuarioSimpleDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setNombreRol(usuario.getRol().getNombreRol());

        return usuarioDTO;
    }

        
    
}
