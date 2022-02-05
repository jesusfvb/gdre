package com.backend.backend.controlador.solicitudes.integrantes;

import com.backend.backend.repositorio.entidad.Integrante;

public class EvaluacionSo {

    private Integer id;

    private Integrante.Evaluacion evaluacion;

    public EvaluacionSo() {
    }

    public Integer getId() {
        return id;
    }

    public Integrante.Evaluacion getEvaluacion() {
        return evaluacion;
    }
}
