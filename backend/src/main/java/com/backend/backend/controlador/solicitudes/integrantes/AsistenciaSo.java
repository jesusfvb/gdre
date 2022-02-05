package com.backend.backend.controlador.solicitudes.integrantes;

import com.backend.backend.repositorio.entidad.Integrante;

public class AsistenciaSo {

    private Integer id;

    private Integrante.Asistencia asistencia;

    public AsistenciaSo() {
    }

    public Integer getId() {
        return id;
    }

    public Integrante.Asistencia getAsistencia() {
        return asistencia;
    }
}
