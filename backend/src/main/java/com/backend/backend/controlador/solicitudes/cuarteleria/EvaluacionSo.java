package com.backend.backend.controlador.solicitudes.cuarteleria;

import com.backend.backend.repositorio.entidad.Cuarteleria;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EvaluacionSo {

    private Integer id;

    private String evaluacion;

    public Cuarteleria getCuarteleria(Cuarteleria cuarteleria) {
        cuarteleria.setEvaluacion(evaluacion);
        return cuarteleria;
    }
}
