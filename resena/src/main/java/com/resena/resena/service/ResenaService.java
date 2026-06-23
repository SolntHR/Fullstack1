package com.resena.resena.service;

import com.resena.resena.model.Resena;
import com.resena.resena.repository.ResenaRepository;
import com.resena.resena.dto.ResenaSimpleDTO;
import com.resena.resena.dto.ResenaListadoDTO;
import com.resena.resena.dto.ResenaDetalleDTO;
import com.resena.resena.dto.ValidacionItemResenaDTO;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    private final ResenaRepository repository;

    private final RestTemplate restTemplate;

    @Value("${carrito.service.url}")
    private String carritoServiceUrl;

    ResenaService(ResenaRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<Resena> listarResenas() {
        return repository.findAll();
    }

    public Optional<Resena> buscarPorIdResena(Integer idResena) {
        return repository.findByIdResena(idResena);
    }

    public List<Resena> buscarPorIdUsuario(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    public List<Resena> buscarPorIdProducto(Integer idProducto) {
        return repository.findByIdProducto(idProducto);
    }

    public Resena agregarResena(Resena resena) {
        String url = carritoServiceUrl
                + "/carrito/items/" + resena.getIdItemCarrito()
                + "/validacion-resena?idUsuario=" + resena.getIdUsuario()
                + "&idProducto=" + resena.getIdProducto();

        ResponseEntity<ValidacionItemResenaDTO> response =
                restTemplate.getForEntity(url, ValidacionItemResenaDTO.class);

        ValidacionItemResenaDTO validacion = response.getBody();

        if (validacion == null || Boolean.FALSE.equals(validacion.getItemValido())) {
            throw new RuntimeException("El item no pertenece al usuario o no corresponde al producto");
        }

        if (Boolean.FALSE.equals(validacion.getCarritoPagado())
                || Boolean.FALSE.equals(validacion.getPagoAprobado())) {
            throw new RuntimeException("La compra aún no está pagada o aprobada");
        }

        if (repository.existsByIdItemCarrito(resena.getIdItemCarrito())) {
            throw new RuntimeException("Ya existe una reseña para este item comprado");
        }

        return repository.save(resena);
    }

    public Optional<Resena> resenaUpdate(Integer idResena, Resena resenaActualizada) {
        return repository.findById(idResena).map(resenaExistente -> {
            resenaExistente.setEstrellas(resenaActualizada.getEstrellas());
            resenaExistente.setComentario(resenaActualizada.getComentario());
            return repository.save(resenaExistente);
        });
    }

    public boolean eliminarResena(Integer idResena) {
        if (repository.existsById(idResena)) {
            repository.deleteById(idResena);
            return true;
        }
        return false;
    }

    public List<ResenaListadoDTO> listarDTO() {
        List<Resena> resenas = repository.findAll();
        List<ResenaListadoDTO> listaDTO = new ArrayList<>();

        for (Resena r : resenas) {
            ResenaListadoDTO dto = new ResenaListadoDTO();
            dto.setIdResena(r.getIdResena());
            dto.setIdUsuario(r.getIdUsuario());
            dto.setIdProducto(r.getIdProducto());
            dto.setIdItemCarrito(r.getIdItemCarrito());
            dto.setEstrellas(r.getEstrellas());
            dto.setComentario(r.getComentario());
            dto.setFechaCreacion(r.getFechaCreacion());
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public List<ResenaSimpleDTO> listarSimpleDTO() {
        List<Resena> resenas = repository.findAll();
        List<ResenaSimpleDTO> listaDTO = new ArrayList<>();

        for (Resena r : resenas) {
            ResenaSimpleDTO dto = new ResenaSimpleDTO();
            dto.setIdResena(r.getIdResena());
            dto.setEstrellas(r.getEstrellas());
            dto.setComentario(r.getComentario());
            listaDTO.add(dto);
    }
        return listaDTO;
    }

    public ResenaDetalleDTO obtenerDetalleDTO(Integer idResena) {
        Resena r = repository.findByIdResena(idResena).orElse(null);

        if (r == null) {
            return null;
        }

        ResenaDetalleDTO dto = new ResenaDetalleDTO();
        dto.setIdResena(r.getIdResena());
        dto.setIdUsuario(r.getIdUsuario());
        dto.setIdProducto(r.getIdProducto());
        dto.setIdItemCarrito(r.getIdItemCarrito());
        dto.setEstrellas(r.getEstrellas());
        dto.setComentario(r.getComentario());
        dto.setFechaCreacion(r.getFechaCreacion());

        return dto;
    }
}
    

