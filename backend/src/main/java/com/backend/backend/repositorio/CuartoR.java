package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Cuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuartoR extends JpaRepository<Cuarto, Integer> {

    List<Cuarto> findAllByApartamento_Id(Integer id);

    boolean existsByNumeroAndApartamento_Id(Integer numero, Integer id);

}
