package com.backend.backend.controlador.solicitudes.cuarteleria;

import com.backend.backend.repositorio.entidad.Cuarteleria;
import com.backend.backend.repositorio.entidad.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CuarteleriaSo {

    private Integer idUsuario;

    private LocalDate fecha;

    public Cuarteleria getCuarteleria(Usuario usuario) {
        Cuarteleria cuarteleria = new Cuarteleria();
        cuarteleria.setFecha(this.fecha);
        cuarteleria.setEvaluacion("No Evaluado");
        cuarteleria.setUsuario(usuario);
        return cuarteleria;
    }
}
