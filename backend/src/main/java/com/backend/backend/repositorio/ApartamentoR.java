package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartamentoR extends JpaRepository<Apartamento, Integer> {

    List<Apartamento> findAllByEdificio_Id(Integer id);

}
