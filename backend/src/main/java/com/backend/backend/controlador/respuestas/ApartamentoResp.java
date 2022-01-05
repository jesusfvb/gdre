package com.backend.backend.controlador.respuestas;

public class ApartamentoResp {

    private Integer id;
    private Integer numero;

    public ApartamentoResp() {
    }

    public ApartamentoResp(Integer id, Integer numero) {
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
