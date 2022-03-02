package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Cuarteleria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CuarteleriaR extends JpaRepository<Cuarteleria, Integer> {

    List<Cuarteleria> findAllByUsuario_Id(Integer id);

    boolean existsByFechaAndUsuario_Id(LocalDate fecha, Integer id);

}
