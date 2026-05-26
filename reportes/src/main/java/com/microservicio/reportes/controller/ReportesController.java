package com.microservicio.reportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.reportes.model.Reportes;

@Repository
public interface ReportesController extends JpaRepository<Reportes, Integer> {

    @Autowired

}
