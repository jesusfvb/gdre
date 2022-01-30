package com.backend.backend.repositorio.entidad;

import javax.persistence.*;

@Entity
public class Integrante extends Entidad {

    private enum Evaluacion {Bien, Regular, Mal}

    @Column
    private String advertencia;

    @Enumerated(EnumType.STRING)
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "guardia_id")
    private Guardia guardia;

    @ManyToOne
    @JoinColumn(name = "participante_id")
    private Usuario participante;

    public Integrante() {
    }

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }

    public String getAdvertencia() {
        return advertencia;
    }

    public Guardia getGuardia() {
        return guardia;
    }

    public void setGuardia(Guardia guardia) {
        this.guardia = guardia;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Override
    public Object convertir() {
        return null;
    }
}
