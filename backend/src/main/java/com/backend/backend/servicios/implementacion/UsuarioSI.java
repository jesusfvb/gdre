package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.usuario.UbicarSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioUpSo;
import com.backend.backend.repositorio.UsuarioR;
import com.backend.backend.repositorio.entidad.Cuarto;
import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.CuartoS;
import com.backend.backend.servicios.UsuarioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioSI implements UsuarioS {

    public final PasswordEncoder passwordEncoder;

    private final UsuarioR usuarioR;

    private final CuartoS cuartoS;

    @Override
    public void quitarCuarto(Integer idCuarto) {
        usuarioR.findAllByCuarto_Id(idCuarto).forEach(usuario -> {
            usuario.setCuarto(null);
            usuarioR.save(usuario);
        });
    }

    @Override
    public List<UsuarioResp> listar() {
        return usuarioR.findAll().parallelStream().map(UsuarioResp::new).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioUbicacionResp> listarUbicados() {
        return usuarioR.findAllByCuartoIsNotNull()
                .parallelStream().map(UsuarioUbicacionResp::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResp> listarPorIdCuarto(Integer idCuarto) {
        return usuarioR.findAllByCuarto_Id(idCuarto).parallelStream()
                .map(UsuarioResp::new).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResp> listarNoUbicados() {
        return usuarioR.findAllByCuartoIsNullAndUbicarIsTrue().parallelStream()
                .map(UsuarioResp::new).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResp> listarPorConfirmar() {
        return usuarioR.findByCuartoIsNullAndUbicarIsNullAndRoles(Usuario.Rol.Estudiante).parallelStream()
                .map(UsuarioResp::new).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResp> listarProfesor() {
        return usuarioR.findByRoles(Usuario.Rol.Profesor).parallelStream()
                .map(UsuarioResp::new).collect(Collectors.toList());
    }

    @Override
    public UsuarioResp ubicar(UbicarSo ubicarSo) {
        Usuario usuario = usuarioR.findById(ubicarSo.getIdUsuario()).get();
        if (usuario.getUbicar() != true) {
            throw new RuntimeException("No se puede ubicar el usuario");
        }
        Cuarto cuarto = cuartoS.getById(ubicarSo.getIdCuarto());
        if (cuarto.getUsuarios().size() >= cuarto.getCapacidad()) {
            throw new RuntimeException("No se puede ubicar el usuario");
        }
        usuario.setCuarto(new Cuarto(ubicarSo.getIdCuarto()));
        return new UsuarioResp(usuarioR.save(usuario));
    }

    @Override
    public UsuarioResp save(UsuarioNewSo usuario) {
        Usuario forSave = usuario.getUsuario();
        forSave.setContrasena(passwordEncoder.encode(forSave.getContrasena()));
        return new UsuarioResp(usuarioR.save(forSave));
    }

    @Override
    public UsuarioResp update(UsuarioUpSo usuario) {
        return new UsuarioResp(usuarioR.save(usuario.getUsuario(usuarioR.findById(usuario.getId()).get())));
    }

    @Override
    public Integer[] desubicar(Integer[] ids) {
        Usuario usuario;
        for (Integer id : ids) {
            usuario = usuarioR.findById(id).get();
            usuario.setCuarto(null);
            usuarioR.save(usuario);
        }
        return ids;
    }

    @Override
    public Integer[] confirmar(Integer[] ids) {
        Usuario usuario;
        for (Integer id : ids) {
            usuario = usuarioR.findById(id).get();
            usuario.setUbicar(true);
            usuarioR.save(usuario);
        }
        return ids;
    }

    @Override
    public Integer[] desconfirmar(Integer[] ids) {
        Usuario usuario;
        for (Integer id : ids) {
            usuario = usuarioR.findById(id).get();
            usuario.setUbicar(null);
            usuarioR.save(usuario);
        }
        return ids;
    }

    @Override
    public Usuario getPorId(Integer id) {
        return usuarioR.findById(id).get();
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
            usuarioR.save(new Usuario(id));
            usuarioR.deleteById(id);
        }
        return ids;
    }

}
