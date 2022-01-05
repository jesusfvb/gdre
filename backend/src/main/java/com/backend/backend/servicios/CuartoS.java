package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.CuartoUpSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuartoS {
    List<CuartoResp> listarPorIdDeApartamento(Integer id);

    CuartoResp salvar(CuartoNewSo cuartoNewSo);

    Integer[] borrar(Integer[] ids);

    CuartoResp update(CuartoUpSo cuartoUpSo);
}
