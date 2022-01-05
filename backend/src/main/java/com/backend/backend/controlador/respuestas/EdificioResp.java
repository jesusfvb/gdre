package com.backend.backend.controlador.respuestas;

public class EdificioResp {
    private Integer id;

    private Integer numero;

    public EdificioResp() {
    }

    public EdificioResp(Integer id, Integer numero) {
        this.id = id;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }
}
