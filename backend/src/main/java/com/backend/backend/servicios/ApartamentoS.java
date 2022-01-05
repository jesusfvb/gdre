package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApartamentoS {
    List<ApartamentoResp> listarPorIdEdificio(Integer id);

    ApartamentoResp salvar(ApartamentoNewSo apartamentoNewSo);

    Integer[] borrar(Integer[] ids);

    ApartamentoResp update(ApartamentoUpSo apartamentoUpSo);
}
