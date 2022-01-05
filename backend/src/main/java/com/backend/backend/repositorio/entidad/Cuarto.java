package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.CuartoUpSo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cuarto extends Entidad {

    @Column
    private Integer numero;

    @Column
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "apartamento_id")
    private Apartamento apartamento;


    public Cuarto() {
    }

    public Cuarto(CuartoNewSo cuartoNewSo, Apartamento apartamento) {
        this.numero = cuartoNewSo.getNumero();
        this.capacidad = cuartoNewSo.getCapacidad();
        this.apartamento = apartamento;
    }

    public Cuarto(CuartoUpSo cuartoUpSo, Apartamento apartamento) {
        super.setId(cuartoUpSo.getId());
        this.numero = cuartoUpSo.getNumero();
        this.capacidad = cuartoUpSo.getCapacidad();
        this.apartamento = apartamento;
    }

    @Override
    public CuartoResp convertir() {
        return new CuartoResp(super.getId(), this.numero, this.capacidad);
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

}
