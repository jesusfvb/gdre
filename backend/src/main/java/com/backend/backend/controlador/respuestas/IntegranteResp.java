package com.backend.backend.controlador.respuestas;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.repositorio.entidad.Integrante;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IntegranteResp {

    private Integer id;

    private Integrante.Asistencia asistencia;

    private String advertencia;

    private Integrante.Evaluacion evaluacion;

    private UsuarioResp participante;

    public IntegranteResp(Integrante integrante) {
        this.id = integrante.getId();
        this.asistencia = integrante.getAsistencia();
        this.advertencia = integrante.getAdvertencia();
        this.evaluacion = integrante.getEvaluacion();
        this.participante = new UsuarioResp(integrante.getParticipante());
    }
}
