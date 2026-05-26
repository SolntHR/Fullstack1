package com.microservicio.usuario.service;

import com.microservicio.usuario.dto.UsuarioDTO.UsuarioSimpleDTO;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario);
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    public List<Usuario> buscarPorApellido(String apellido) {
        return usuarioRepository.findByApellido(apellido);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    public Usuario agregarUsuario(Usuario usuario) {
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(Integer idUsuario, Usuario usuarioActualizado) {
    return usuarioRepository.findByIdUsuario(idUsuario).map(usuario -> {
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setPassword(usuarioActualizado.getPassword());
        
        usuario.setRol(usuarioActualizado.getRol());
        return usuarioRepository.save(usuario);
    });
}

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
        UsuarioSimpleDTO usuarioDTO = new UsuarioSimpleDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setNombreRol(usuario.getRol().getNombreRol());
        return usuarioDTO;
    }

}
