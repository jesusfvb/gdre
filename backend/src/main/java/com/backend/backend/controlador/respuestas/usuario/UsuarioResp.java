package com.backend.backend.controlador.respuestas.usuario;

import com.backend.backend.repositorio.entidad.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UsuarioResp {

    private Integer id;

    private String nombre;

    private String usuario;

    private String solapin;

    private String rol;

    public UsuarioResp(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.usuario = usuario.getUsuario();
        this.solapin = usuario.getSolapin();
        this.rol = usuario.getRoles().size() == 0 ? null : usuario.getRoles().get(1).name();
    }

}
