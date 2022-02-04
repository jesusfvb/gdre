package com.backend.backend.controlador.solicitudes.guardian;

import java.time.LocalDate;

public class GuardiaDocenteUpSol {

    private Integer id;

    private Integer idCoordinador;

    private LocalDate fecha;

    public GuardiaDocenteUpSol() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdCoordinador() {
        return idCoordinador;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
