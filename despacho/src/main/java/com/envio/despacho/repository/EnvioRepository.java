package com.envio.despacho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.envio.despacho.model.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {

}
