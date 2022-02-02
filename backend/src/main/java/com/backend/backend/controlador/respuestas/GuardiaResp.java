package com.backend.backend.controlador.respuestas;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;

import java.time.LocalDate;

public class GuardiaResp {

    private Integer id;

    private UsuarioResp coordinador;

    private LocalDate fecha;

    public GuardiaResp() {
    }

    public GuardiaResp(Integer id, LocalDate fecha, UsuarioResp coordinador) {
        this.id = id;
        this.fecha = fecha;
        this.coordinador = coordinador;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public UsuarioResp getCoordinador() {
        return coordinador;
    }
}
