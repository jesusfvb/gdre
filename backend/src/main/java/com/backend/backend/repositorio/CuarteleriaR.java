package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Cuarteleria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuarteleriaR extends JpaRepository<Cuarteleria, Integer> {

}
