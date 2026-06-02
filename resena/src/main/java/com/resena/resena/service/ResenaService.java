package com.resena.resena.service;

import com.resena.resena.model.Resena;
import com.resena.resena.repository.ResenaRepository;

import lombok.Value;

import com.resena.resena.dto.ResenaCrearDTO;
import com.resena.resena.dto.ResenaResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ResenaService {

    @Autowired
    private ResenaRepository repository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value


}
