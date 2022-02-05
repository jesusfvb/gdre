package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.IntegranteResp;
import com.backend.backend.controlador.solicitudes.integrantes.AdvertenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.AsistenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.EvaluacionSo;
import com.backend.backend.controlador.solicitudes.integrantes.IntegranteNewSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IntegranteS {

    List<IntegranteResp> listarPorGuardia(Integer id);

    IntegranteResp save(IntegranteNewSo integrante);

    IntegranteResp asistencia(AsistenciaSo asistencia);

    IntegranteResp evaluacion(EvaluacionSo evaluacion);

    IntegranteResp advertencia(AdvertenciaSo advertencia);

    Integer[] delete(Integer[] ids);
}
