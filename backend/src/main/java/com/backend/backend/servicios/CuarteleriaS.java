package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaUpSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.EvaluacionSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuarteleriaS {

    List<CuarteleriaResp> listar();

    List<CuarteleriaResp> listar(Integer id);

    CuarteleriaResp salvar(CuarteleriaSo cuarteleria);

    CuarteleriaResp update(CuarteleriaUpSo cuarteleria);

    CuarteleriaResp evaluar(EvaluacionSo evaluacion);

    Integer[] delete(Integer[] ids);
}
