package com.backend.backend.controlador.solicitudes.integrantes;

import com.backend.backend.repositorio.entidad.Integrante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EvaluacionSo {

    private Integer id;

    private Integrante.Evaluacion evaluacion;

    @JsonIgnore
    public Integrante getIntegrante(Integrante integrante) {
        integrante.setEvaluacion(this.evaluacion);
        return integrante;
    }

}
