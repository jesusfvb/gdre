package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.CuarteleriaSo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Cuarteleria extends Entidad {

    @Column
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cuarteleria() {
    }

    public Cuarteleria(CuarteleriaSo cuarteleria) {
        this.fecha = cuarteleria.getFecha();
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

    @Override
    public CuarteleriaResp convertir() {
        return new CuarteleriaResp(super.getId(), this.fecha, usuario.getNombre());
    }
}
