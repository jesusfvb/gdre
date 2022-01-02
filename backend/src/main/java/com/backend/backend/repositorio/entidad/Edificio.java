package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Edificio extends Entidad {
    @Column
    private Integer numero;

    public Edificio() {
    }

    public Edificio(EdificioNewSo solicitud) {
        this.numero = solicitud.getNumero();
    }

    public Edificio(EdificioUpSo solicitud) {
        super.setId(solicitud.getId());
        this.numero = solicitud.getNumero();
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public EdificioResp convertir() {
        return new EdificioResp(super.getId(), numero);
    }
}
