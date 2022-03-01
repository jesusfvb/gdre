package com.backend.backend.controlador.respuestas;

import com.backend.backend.repositorio.entidad.Cuarteleria;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CuarteleriaResp {

    private Integer id;

    private String nombre;

    private LocalDate fecha;

    private String evaluacion;

    public CuarteleriaResp(Cuarteleria cuarteleria) {
        this.id = cuarteleria.getId();
        this.nombre = cuarteleria.getUsuario() != null ? cuarteleria.getUsuario().getNombre() : null;
        this.fecha = cuarteleria.getFecha();
        this.evaluacion = cuarteleria.getEvaluacion();
    }
}
