package com.microservicio.usuario.service;

import com.microservicio.usuario.dto.UsuarioDTO.UsuarioSimpleDTO;
import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.model.Usuario;
import com.microservicio.usuario.repository.RolRepository;
import com.microservicio.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = null;
    }

    public List<Usuario> listarUsuarios() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer idUsuario) {
        log.info("Buscando usuario con id: {}", idUsuario);
        Optional<Usuario> usuario = usuarioRepository.findByIdUsuario(idUsuario);

        if (usuario.isPresent()) {
            log.info("Usuario encontrado con id: {}", idUsuario);
        } else {
            log.warn("No se encontró usuario con id: {}", idUsuario);
        }

        return usuario;
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        log.info("Buscando usuarios por nombre: {}", nombre);
        return usuarioRepository.findByNombre(nombre);
    }

    public List<Usuario> buscarPorApellido(String apellido) {
        log.info("Buscando usuarios por apellido: {}", apellido);
        return usuarioRepository.findByApellido(apellido);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        log.info("Buscando usuario por email: {}", email);
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    public Usuario agregarUsuario(Usuario usuario) {
    log.info("Intentando registrar usuario con email: {}", usuario.getEmail());

        if (usuarioRepository.findByEmailIgnoreCase(usuario.getEmail()).isPresent()) {
            log.warn("Intento de registro con email ya existente: {}", usuario.getEmail());
            throw new IllegalArgumentException("El email ya está registrado");
        }

        try {

            String nombreRol = usuario.getRol().getNombreRol();
            Rol rolExistente = rolRepository.findByNombreRol(nombreRol)
                    .orElseThrow(() -> new IllegalArgumentException("El rol '" + nombreRol + "' no existe"));

            usuario.setRol(rolExistente);
            
            String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordEncriptada);

            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            log.info("Usuario creado exitosamente con id: {}", usuarioGuardado.getIdUsuario());

            return usuarioGuardado;
        } catch (Exception e) {
            log.error("Error al crear usuario con email: {}", usuario.getEmail(), e);
            throw new RuntimeException("Error al crear el usuario");
        }
    }   

    public Optional<Usuario> actualizarUsuario(Integer idUsuario, Usuario usuarioActualizado) {
        log.info("Intentando actualizar usuario con id: {}", idUsuario);

        return usuarioRepository.findByIdUsuario(idUsuario).map(usuario -> {
            Optional<Usuario> usuarioConMismoEmail = usuarioRepository.findByEmailIgnoreCase(usuarioActualizado.getEmail());

            if (usuarioConMismoEmail.isPresent() &&
                    !usuarioConMismoEmail.get().getIdUsuario().equals(idUsuario)) {
                log.warn("Intento de actualizar usuario con email ya existente: {}", usuarioActualizado.getEmail());
                throw new IllegalArgumentException("Ya existe un usuario con ese email");
            }

            try {
                usuario.setNombre(usuarioActualizado.getNombre());
                usuario.setApellido(usuarioActualizado.getApellido());
                usuario.setEmail(usuarioActualizado.getEmail());

                if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isBlank()) {
                    usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
                    log.info("Contraseña actualizada para usuario con id: {}", idUsuario);
                }

                usuario.setRol(usuarioActualizado.getRol());

                Usuario usuarioGuardado = usuarioRepository.save(usuario);
                log.info("Usuario actualizado exitosamente con id: {}", usuarioGuardado.getIdUsuario());

                return usuarioGuardado;
            } catch (Exception e) {
                log.error("Error al actualizar usuario con id: {}", idUsuario, e);
                throw new RuntimeException("Error al actualizar el usuario");
            }
        });
    }

    public boolean eliminarUsuario(Integer idUsuario) {
        log.info("Intentando eliminar usuario con id: {}", idUsuario);

        if (usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
            log.info("Usuario eliminado exitosamente con id: {}", idUsuario);
            return true;
        }

        log.warn("No se pudo eliminar. Usuario no encontrado con id: {}", idUsuario);
        return false;
    }

    public UsuarioSimpleDTO obtenerDetalleSimple(Integer idUsuario) {
        log.info("Obteniendo detalle simple del usuario con id: {}", idUsuario);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdUsuario(idUsuario);

        if (usuarioOpt.isEmpty()) {
            log.warn("No se encontró usuario para detalle simple con id: {}", idUsuario);
            return null;
        }

        Usuario usuario = usuarioOpt.get();
        UsuarioSimpleDTO usuarioDTO = new UsuarioSimpleDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setNombreRol(usuario.getRol().getNombreRol());

        log.info("Detalle simple generado para usuario con id: {}", idUsuario);
        return usuarioDTO;
    }
}