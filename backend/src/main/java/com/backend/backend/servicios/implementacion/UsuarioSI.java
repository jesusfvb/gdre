package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.UsuarioResp;
import com.backend.backend.controlador.solicitudes.UbicarSo;
import com.backend.backend.repositorio.UsuarioR;
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
    public List<UsuarioResp> listarPorIdCuarto(Integer idCuarto) {
        List<UsuarioResp> salida = new LinkedList<>();
        usuarioR.findAllByCuarto_Id(idCuarto).forEach(usuario -> {
            salida.add(usuario.convertir());
        });
        return salida;
    }

    @Override
    public UsuarioResp ubicar(UbicarSo ubicarSo) {
        return usuarioR.save(usuarioR.getById(ubicarSo.getIdUsuario()).addCuarto(cuartoS.getById(ubicarSo.getIdCuarto()))).convertir();
    }

    @Override
    public List<UsuarioResp> listarNoUbicados() {
        List<UsuarioResp> salida = new LinkedList<>();
        usuarioR.findAllByCuartoIsNull().forEach(usuario -> {
            salida.add(usuario.convertir());
        });
        return salida;
    }

    @Override
    public Integer[] desubicar(Integer[] ids) {
        for (Integer id : ids) {
            usuarioR.save(usuarioR.getById(id).cuartoNull());
        }
        return ids;
    }
}
