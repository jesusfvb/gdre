package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;

import javax.persistence.*;

@Entity
public class Apartamento extends Entidad {

    @Column
    private Integer numero;

    @ManyToOne()
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;


    public Apartamento() {
    }

    public Apartamento(ApartamentoNewSo apartamentoNewSo, Edificio edificio) {
        this.numero = apartamentoNewSo.getNumero();
        this.edificio = edificio;
    }

    public Apartamento(ApartamentoUpSo apartamentoUpSo, Edificio edificio) {
        super.setId(apartamentoUpSo.getId());
        this.numero = apartamentoUpSo.getNumero();
        this.edificio = edificio;
    }

    @Override
    public ApartamentoResp convertir() {
        return new ApartamentoResp(super.getId(), numero);
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

}
