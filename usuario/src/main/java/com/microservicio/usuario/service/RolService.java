package com.microservicio.usuario.service;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.repository.RolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RolService {

    private final RolRepository rolRepository;

    RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> listarRoles() {
        log.info("Listando todos los roles");
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarPorIdRol(Integer idRol) {
        log.info("Buscando rol con id: {}", idRol);
        Optional<Rol> rol = rolRepository.findByIdRol(idRol);

        if (rol.isPresent()) {
            log.info("Rol encontrado con id: {}", idRol);
        } else {
            log.warn("No se encontró rol con id: {}", idRol);
        }

        return rol;
    }

    public Optional<Rol> buscarPorNombreRol(String nombreRol) {
        log.info("Buscando rol con nombre: {}", nombreRol);
        return rolRepository.findByNombreRol(nombreRol);
    }

    public Rol agregarRol(Rol rol) {
        log.info("Intentando crear rol con nombre: {}", rol.getNombreRol());

        if (rolRepository.findByNombreRol(rol.getNombreRol()).isPresent()) {
            log.warn("Intento de crear rol duplicado con nombre: {}", rol.getNombreRol());
            throw new IllegalArgumentException("El rol ya existe");
        }

        try {
            Rol nuevoRol = rolRepository.save(rol);
            log.info("Rol creado exitosamente con id: {}", nuevoRol.getIdRol());
            return nuevoRol;
        } catch (Exception e) {
            log.error("Error al crear rol con nombre: {}", rol.getNombreRol(), e);
            throw new RuntimeException("Error al crear el rol");
        }
    }

    public Optional<Rol> actualizarRol(Integer idRol, Rol rolActualizado) {
        log.info("Intentando actualizar rol con id: {}", idRol);

        return rolRepository.findByIdRol(idRol).map(rolExistente -> {
            Optional<Rol> rolConMismoNombre = rolRepository.findByNombreRol(rolActualizado.getNombreRol());

            if (rolConMismoNombre.isPresent() && !rolConMismoNombre.get().getIdRol().equals(idRol)) {
                log.warn("Intento de actualizar rol con nombre ya existente: {}", rolActualizado.getNombreRol());
                throw new IllegalArgumentException("Ya existe un rol con ese nombre");
            }

            try {
                rolExistente.setNombreRol(rolActualizado.getNombreRol());
                Rol rolGuardado = rolRepository.save(rolExistente);
                log.info("Rol actualizado exitosamente con id: {}", rolGuardado.getIdRol());
                return rolGuardado;
            } catch (Exception e) {
                log.error("Error al actualizar rol con id: {}", idRol, e);
                throw new RuntimeException("Error al actualizar el rol");
            }
        });
    }

    public boolean eliminarRol(Integer idRol) {
        log.info("Intentando eliminar rol con id: {}", idRol);

        try {
            if (rolRepository.existsById(idRol)) {
                rolRepository.deleteById(idRol);
                log.info("Rol eliminado exitosamente con id: {}", idRol);
                return true;
            }

            log.warn("No se pudo eliminar. Rol no encontrado con id: {}", idRol);
            return false;
        } catch (Exception e) {
            log.error("Error al eliminar rol con id: {}", idRol, e);
            throw new RuntimeException("Error al eliminar el rol");
        }
    }
}