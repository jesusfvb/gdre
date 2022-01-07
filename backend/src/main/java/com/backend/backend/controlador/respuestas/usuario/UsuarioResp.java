package com.backend.backend.controlador.respuestas.usuario;

public class UsuarioResp {

    private Integer id;

    private String nombre;

    public UsuarioResp(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
