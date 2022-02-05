package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Integrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegranteR extends JpaRepository<Integrante, Integer> {
    List<Integrante> findAllByGuardia_Id(Integer id);
}
    