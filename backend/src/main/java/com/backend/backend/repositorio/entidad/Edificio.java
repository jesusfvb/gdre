package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Edificio extends Entidad {
    @Column
    private Integer numero;

    @OneToMany(mappedBy = "edificio", cascade = {CascadeType.REMOVE,}, orphanRemoval = true)
    private List<Apartamento> apartamentos = new ArrayList<>();


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

    public List<Apartamento> getApartamentos() {
        return apartamentos;
    }

    public void setApartamentos(List<Apartamento> apartamentos) {
        this.apartamentos = apartamentos;
    }

    public EdificioResp convertir() {
        return new EdificioResp(super.getId(), numero);
    }
}
