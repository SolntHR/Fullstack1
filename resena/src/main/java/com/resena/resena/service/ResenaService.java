package com.resena.resena.service;

import com.resena.resena.model.Resena;
import com.resena.resena.repository.ResenaRepository;
import com.resena.resena.dto.ResenaSimpleDTO;
import com.resena.resena.dto.ResenaListadoDTO;
import com.resena.resena.dto.ResenaDetalleDTO;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pago.service.url}")
    private String pagoServiceUrl;

    public List<Resena> listarResenas() {
        return repository.findAll();
    }

    public Optional<Resena> buscarPorIdResena(Integer idResena) {
        return repository.findById(idResena);
    }

    public List<Resena> buscarPorIdUsuario(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    public List<Resena> buscarPorIdProducto(Integer idProducto) {
        return repository.findByIdProducto(idProducto);
    }

    public Resena agregarResena(Resena resena) {
        String url = pagoServiceUrl + "/carrito/pagos/validar-compra/"
                + resena.getIdPago() + "/"
                + resena.getIdUsuario() + "/"
                + resena.getIdProducto();

        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        Boolean compraValida = response.getBody();

        if (compraValida == null || !compraValida) {
            throw new RuntimeException("El pago no corresponde a una compra válida para ese usuario y producto");
        }

        if (repository.existsByIdUsuarioAndIdProducto(resena.getIdUsuario(), resena.getIdProducto())) {
            throw new RuntimeException("El usuario ya registró una reseña para este producto");
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
            dto.setIdPago(r.getIdPago());
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
        dto.setIdPago(r.getIdPago());
        dto.setEstrellas(r.getEstrellas());
        dto.setComentario(r.getComentario());
        dto.setFechaCreacion(r.getFechaCreacion());

        return dto;
    }
}
    

