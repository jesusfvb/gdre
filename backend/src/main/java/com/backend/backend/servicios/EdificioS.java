package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EdificioS {

    List<EdificioResp> listar();

    EdificioResp salvar(EdificioNewSo solicitud);

    Integer[] borrar(Integer[] ids);

    EdificioResp update(EdificioUpSo solicitud);
}
