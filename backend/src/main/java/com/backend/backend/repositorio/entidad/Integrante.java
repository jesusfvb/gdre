package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.IntegranteResp;
import com.backend.backend.controlador.solicitudes.integrantes.IntegranteNewSo;

import javax.persistence.*;

@Entity
public class Integrante extends Entidad {

    public enum Evaluacion {Bien, Regular, Mal, Pendiente}

    public enum Asistencia {Presente, Ausente, Pendiente}

    @Enumerated(EnumType.STRING)
    private Asistencia asistencia;

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

    public Integrante(IntegranteNewSo integrante) {
        this.asistencia = Asistencia.Pendiente;
        this.advertencia = null;
        this.evaluacion = Evaluacion.Pendiente;
        this.guardia = new Guardia(integrante.getIdGuardia());
        this.participante = new Usuario(integrante.getIdParticipante());
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

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public void setAdvertencia(String advertencia) {
        this.advertencia = advertencia;
    }

    @Override
    public IntegranteResp convertir() {
        return new IntegranteResp(this.getId(), this.asistencia, this.advertencia, this.evaluacion, this.participante.convertir());
    }
}
