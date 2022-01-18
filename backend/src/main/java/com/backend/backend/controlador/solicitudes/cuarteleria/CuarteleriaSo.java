package com.backend.backend.controlador.solicitudes.cuarteleria;

import com.backend.backend.repositorio.entidad.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class CuarteleriaSo {

    private Integer idUsuario;

    private LocalDate fecha;

    public CuarteleriaSo() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

}
