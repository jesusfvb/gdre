package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.UbicarSo;
import com.backend.backend.repositorio.UsuarioR;
import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.CuartoS;
import com.backend.backend.servicios.UsuarioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UsuarioSI implements UsuarioS {

    @Autowired
    private UsuarioR usuarioR;

    @Autowired
    private CuartoS cuartoS;

    @Override
    public void quitarCuarto(Integer idCuarto) {
        usuarioR.findAllByCuarto_Id(idCuarto).forEach(usuario -> {
            usuarioR.save(usuario.cuartoNull());
        });
    }

    @Override
    public List<UsuarioUbicacionResp> listarUbicados() {
        List<UsuarioUbicacionResp> salida = new LinkedList<>();
        usuarioR.findAllByCuartoIsNotNull().forEach(usuario -> {
            salida.add(usuario.convertir2());
        });
        return salida;
    }

    @Override
    public List<UsuarioResp> listarPorIdCuarto(Integer idCuarto) {
        List<UsuarioResp> salida = new LinkedList<>();
        usuarioR.findAllByCuarto_Id(idCuarto).forEach(usuario -> {
            salida.add(usuario.convertir());
        });
        return salida;
    }

    @Override
    public List<UsuarioResp> listarNoUbicados() {
        List<UsuarioResp> salida = new LinkedList<>();
        usuarioR.findAllByCuartoIsNullAndUbicarIsTrue().forEach(usuario -> {
            salida.add(usuario.convertir());
        });
        return salida;
    }

    @Override
    public List<UsuarioResp> listarPorConfirmar() {
        List<UsuarioResp> salida = new LinkedList<>();
        usuarioR.findAllByCuartoIsNullAndUbicarIsNull().forEach(usuario -> {
            salida.add(usuario.convertir());
        });
        return salida;
    }

    @Override
    public UsuarioResp ubicar(UbicarSo ubicarSo) {
        return usuarioR.save(usuarioR.getById(ubicarSo.getIdUsuario()).addCuarto(cuartoS.getById(ubicarSo.getIdCuarto()))).convertir();
    }

    @Override
    public Integer[] desubicar(Integer[] ids) {
        for (Integer id : ids) {
            usuarioR.save(usuarioR.getById(id).cuartoNull());
        }
        return ids;
    }

    @Override
    public Integer[] confirmar(Integer[] ids) {
        for (Integer id : ids) {
            usuarioR.save(usuarioR.getById(id).confirmar());
        }
        return ids;
    }

    @Override
    public Integer[] desconfirmar(Integer[] ids) {
        for (Integer id : ids) {
            usuarioR.save(usuarioR.getById(id).desconfirmar());
        }
        return ids;
    }

    @Override
    public Usuario getPorId(Integer id) {
        return usuarioR.getById(id);
    }
}
