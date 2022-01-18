package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.CuarteleriaSo;
import com.backend.backend.controlador.solicitudes.CuarteleriaUpSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuarteleriaS {

    List<CuarteleriaResp> listar();

    CuarteleriaResp salvar(CuarteleriaSo cuarteleria);

    CuarteleriaResp update(CuarteleriaUpSo cuarteleria);

    Integer[] delete(Integer[] ids);
}
