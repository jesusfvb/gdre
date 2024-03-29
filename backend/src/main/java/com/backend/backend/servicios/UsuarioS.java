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

    List<UsuarioUbicacionResp> listarUbicados();

    List<UsuarioResp> listarProfesor();

    List<UsuarioResp> listar();

    List<UsuarioResp> listarPorIdCuarto(Integer idCuarto);

    List<UsuarioResp> listarNoUbicados();

    List<UsuarioResp> listarPorConfirmar();

    UsuarioResp ubicar(UbicarSo ubicarSo);

    UsuarioResp save(UsuarioNewSo usuario);

    UsuarioResp update(UsuarioUpSo usuario);

    Usuario getPorId(Integer id);

    Usuario getByUsuario(String username);

    Integer[] desubicar(Integer[] ids);

    Integer[] confirmar(Integer[] ids);

    Integer[] desconfirmar(Integer[] ids);

    Integer[] borrar(Integer[] ids);
}
