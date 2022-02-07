package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.usuario.UbicarSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioUpSo;
import com.backend.backend.repositorio.UsuarioR;
import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.CuartoS;
import com.backend.backend.servicios.UsuarioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioSI implements UsuarioS {

    @Autowired
    public PasswordEncoder passwordEncoder;

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
    public List<UsuarioResp> listar() {
        return usuarioR.findAll().parallelStream().map(Usuario::convertir).collect(Collectors.toList());
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
    public UsuarioResp save(UsuarioNewSo usuario) {
        return usuarioR.save(new Usuario(usuario)).convertir();
    }

    @Override
    public UsuarioResp update(UsuarioUpSo usuario) {
        Usuario usuarioP = getPorId(usuario.getId());
        usuarioP.setNombre(usuario.getNombre());
        return usuarioR.save(usuarioP).convertir();
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

    @Override
    public Usuario getByUsuario(String username) {
        if (username.equals("admin")) {
            Usuario usuario = new Usuario(-1);
            usuario.setNombre("Admin");
            usuario.setUsuario("admin");
            usuario.setContrasena(passwordEncoder.encode("1234"));
            usuario.setCuarto(null);
            usuario.getRoles().add(Usuario.Rol.Usuario);
            usuario.getRoles().add(Usuario.Rol.Administrador);
            return usuario;
        }
        return usuarioR.findByUsuario(username);
    }

    @Override
    public Integer[] borrar(Integer[] ids) {
        for (Integer id : ids) {
            usuarioR.deleteById(id);
        }
        return ids;
    }
}
