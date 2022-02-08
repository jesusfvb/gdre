package com.backend.backend.controlador.respuestas.usuario;

import com.backend.backend.repositorio.entidad.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsuarioUbicacionResp {

    private Integer id;

    private String nombre;

    private Integer edificio;

    private Integer apartamento;

    private Integer cuarto;

    public UsuarioUbicacionResp(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        if (usuario.getCuarto() != null) {
            this.cuarto = usuario.getCuarto().getNumero();
            this.apartamento = usuario.getCuarto().getApartamento().getNumero();
            this.edificio = usuario.getCuarto().getApartamento().getEdificio().getNumero();
        }
    }
}
