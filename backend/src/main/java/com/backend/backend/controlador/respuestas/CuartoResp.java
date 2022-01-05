package com.backend.backend.controlador.respuestas;

public class CuartoResp {

    private Integer id;

    private Integer numero;

    private Integer capacidad;

    public CuartoResp() {
    }

    public CuartoResp(Integer id, Integer numero, Integer capacidad) {
        this.id = id;
        this.numero = numero;
        this.capacidad = capacidad;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }
}
