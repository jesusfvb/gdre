package com.backend.backend.controlador.respuestas;

import java.time.LocalDate;

public class CuarteleriaResp {

    private Integer id;

    private String nombre;

    private LocalDate fecha;

    public CuarteleriaResp() {
    }

    public CuarteleriaResp(Integer id, LocalDate fecha, String nombre) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
