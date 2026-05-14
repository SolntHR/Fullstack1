package com.microservicio.usuario.service;

import com.microservicio.usuario.model.Rol;
import com.microservicio.usuario.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // GET: Lista a todos los roles
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    // GET: Buscar por ID
    public Optional<Rol> buscarPorIdRol(Integer idRol) {
        return rolRepository.findByIdRol(idRol);
    }

    // GET: Buscar por nombre
    public Optional<Rol> buscarPorNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    // POST: Agregar un nuevo rol
    public Rol agregarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // PUT: Actualizar un rol existente
    public Optional<Rol> actualizarRol(Integer idRol, Rol rolActualizado) {
        return rolRepository.findByIdRol(idRol).map(rolExistente -> {
            rolExistente.setNombreRol(rolActualizado.getNombreRol());
                return rolRepository.save(rolExistente);
        });
    }

    // DELETE: Eliminar un rol por ID
    public boolean eliminarRol(Integer idRol) {
        if(rolRepository.existsById(idRol)) {
            rolRepository.deleteById(idRol);
            return true;
        }
        return false;
    }

}
