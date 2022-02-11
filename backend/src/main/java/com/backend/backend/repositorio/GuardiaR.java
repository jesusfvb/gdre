package com.backend.backend.repositorio;

import com.backend.backend.repositorio.entidad.Guardia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardiaR extends JpaRepository<Guardia, Integer> {

    List<Guardia> findByUbicacion(Guardia.Ubicacion ubicacion);

    List<Guardia> findByUbicacionAndIntegrantes_Participante_Id(Guardia.Ubicacion ubicacion, Integer id);

    List<Guardia> findByUbicacionAndCoordinador_Id(Guardia.Ubicacion ubicacion, Integer id);

}
