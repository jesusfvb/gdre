package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.usuario.UbicarSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioUpSo;
import com.backend.backend.repositorio.entidad.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioS {

    void quitarCuarto(Integer idCuarto);

    List<UsuarioResp> listar();

    List<UsuarioUbicacionResp> listarUbicados();

    List<UsuarioResp> listarPorIdCuarto(Integer idCuarto);

    List<UsuarioResp> listarNoUbicados();

    List<UsuarioResp> listarPorConfirmar();

    UsuarioResp ubicar(UbicarSo ubicarSo);

    UsuarioResp save(UsuarioNewSo usuario);

    UsuarioResp update(UsuarioUpSo usuario);

    Integer[] desubicar(Integer[] ids);

    Integer[] confirmar(Integer[] ids);

    Integer[] desconfirmar(Integer[] ids);

    Usuario getPorId(Integer id);

    Integer[] borrar(Integer[] ids);

}
