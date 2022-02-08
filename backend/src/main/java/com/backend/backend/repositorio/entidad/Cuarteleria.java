package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaSo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Cuarteleria extends Entidad {

    @Column
    private LocalDate fecha;

    @Column
    private String evaluacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cuarteleria() {
    }

    public Cuarteleria(CuarteleriaSo cuarteleria) {
        this.fecha = cuarteleria.getFecha();
        this.evaluacion = "No Evaluado";
        this.usuario = new Usuario(cuarteleria.getIdUsuario());
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public CuarteleriaResp convertir() {
        return new CuarteleriaResp(super.getId(), this.fecha, evaluacion, usuario.getNombre());
    }
}
