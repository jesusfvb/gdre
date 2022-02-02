package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Guardia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardiaR extends JpaRepository<Guardia, Integer> {

    @Query("select g from Guardia g where g.ubicacion='Residencia'")
    List<Guardia> findAllByUbicacionIsResidencia();

    @Query("select g from Guardia g where g.ubicacion='Docente'")
    List<Guardia> findAllByUbicacionIsDocente();

}
