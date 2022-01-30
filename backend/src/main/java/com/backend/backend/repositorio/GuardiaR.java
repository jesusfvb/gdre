package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Guardia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardiaR extends JpaRepository<Guardia, Integer> {
}
