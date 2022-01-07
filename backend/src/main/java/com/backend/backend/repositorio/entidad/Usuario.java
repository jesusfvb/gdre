package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.UsuarioResp;

import javax.persistence.*;

@Entity
public class Usuario extends Entidad {

    private String nombre;

    @ManyToOne()
    @JoinColumn(name = "cuarto_id")
    private Cuarto cuarto;

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

    @Override
    public UsuarioResp convertir() {
        return new UsuarioResp(super.getId(), this.nombre);
    }

    public Usuario cuartoNull() {
        this.cuarto = null;
        return this;
    }

    public Usuario addCuarto(Cuarto cuarto) {
        setCuarto(cuarto);
        return this;
    }
}
