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

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarPorIdRol(Integer idRol) {
        return rolRepository.findByIdRol(idRol);
    }

    public Optional<Rol> buscarPorNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    public Rol agregarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Optional<Rol> actualizarRol(Integer idRol, Rol rolActualizado) {
        return rolRepository.findByIdRol(idRol).map(rolExistente -> {
            rolExistente.setNombreRol(rolActualizado.getNombreRol());
                return rolRepository.save(rolExistente);
        });
    }

    public boolean eliminarRol(Integer idRol) {
        if(rolRepository.existsById(idRol)) {
            rolRepository.deleteById(idRol);
            return true;
        }
        return false;
    }

}
