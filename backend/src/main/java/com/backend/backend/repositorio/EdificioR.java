package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdificioR extends JpaRepository<Edificio, Integer> {
}
