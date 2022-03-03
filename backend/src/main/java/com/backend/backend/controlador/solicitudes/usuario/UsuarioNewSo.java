package com.backend.backend.controlador.solicitudes.usuario;

import com.backend.backend.repositorio.entidad.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UsuarioNewSo {

    private String nombre;

    private String username;

    private String solapin;

    private Usuario.Rol rol;

    @JsonIgnore
    public Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre(this.nombre);
        usuario.setUsuario(this.username);
        usuario.setSolapin(this.solapin);
        usuario.setContrasena(this.username);
        usuario.getRoles().add(Usuario.Rol.Usuario);
        usuario.getRoles().add(this.rol);
        return usuario;
    }
}
