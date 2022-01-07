package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;
import com.backend.backend.repositorio.entidad.Cuarto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuartoS {

    Cuarto getById(Integer idCuarto);

    List<CuartoResp> listarPorIdDeApartamento(Integer id);

    CuartoResp salvar(CuartoNewSo cuartoNewSo);

    Integer[] borrar(Integer[] ids);

    CuartoResp update(CuartoUpSo cuartoUpSo);
}
