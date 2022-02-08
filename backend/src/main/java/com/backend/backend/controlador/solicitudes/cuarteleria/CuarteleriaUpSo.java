package com.backend.backend.controlador.solicitudes.cuarteleria;

import com.backend.backend.repositorio.entidad.Cuarteleria;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CuarteleriaUpSo {

    Integer id;

    LocalDate fecha;

    public Cuarteleria getCuarteleria(Cuarteleria cuarteleria) {
        cuarteleria.setFecha(this.fecha);
        cuarteleria.setEvaluacion("No Evaluado");
        return cuarteleria;
    }
}
