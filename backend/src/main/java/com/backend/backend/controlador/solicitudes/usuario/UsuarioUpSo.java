package com.backend.backend.controlador.solicitudes.usuario;

import com.backend.backend.repositorio.entidad.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UsuarioUpSo {

    private Integer id;

    private String nombre;

    private String username;

    private String solapin;

    private Usuario.Rol rol;

    @JsonIgnore
    public Usuario getUsuario(Usuario usuario) {
        usuario.setNombre(this.nombre);
        usuario.setUsuario(this.username);
        usuario.setSolapin(this.solapin);
        usuario.getRoles().removeIf(rol1 -> !rol1.equals(Usuario.Rol.Usuario));
        usuario.getRoles().add(rol);
        return usuario;
    }

}
