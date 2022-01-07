package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.UsuarioResp;
import com.backend.backend.controlador.solicitudes.UbicarSo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioS {

    void quitarCuarto(Integer idCuarto);

    List<UsuarioResp> listarPorIdCuarto(Integer idCuarto);

    UsuarioResp ubicar(UbicarSo ubicarSo);

    List<UsuarioResp> listarNoUbicados();

    Integer[] desubicar(Integer[] ids);
}
