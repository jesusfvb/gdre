package com.backend.backend.controlador.respuestas;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.repositorio.entidad.Integrante;

public class IntegranteResp {

    private Integer id;

    private Integrante.Asistencia asistencia;

    private String advertencia;

    private Integrante.Evaluacion evaluacion;

    private UsuarioResp participante;

    public IntegranteResp() {
    }

    public IntegranteResp(Integer id, Integrante.Asistencia asistencia, String advertencia, Integrante.Evaluacion evaluacion, UsuarioResp participante) {
        this.id = id;
        this.asistencia = asistencia;
        this.advertencia = advertencia;
        this.evaluacion = evaluacion;
        this.participante = participante;
    }

    public Integer getId() {
        return id;
    }

    public Integrante.Asistencia getAsistencia() {
        return asistencia;
    }

    public String getAdvertencia() {
        return advertencia;
    }

    public Integrante.Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public UsuarioResp getParticipante() {
        return participante;
    }

    public void setParticipante(UsuarioResp participante) {
        this.participante = participante;
    }
}
