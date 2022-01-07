package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;

import javax.persistence.*;

@Entity
public class Usuario extends Entidad {

    @Column
    private String nombre;

    @Column()
    private Boolean ubicar;

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

    public UsuarioUbicacionResp convertir2() {
        Integer edificio = (this.cuarto != null) ? this.cuarto.getApartamento().getEdificio().getNumero() : null;
        Integer apartamento = (this.cuarto != null) ? this.cuarto.getApartamento().getNumero() : null;
        Integer cuarto = (this.cuarto != null) ? this.cuarto.getId() : null;
        return new UsuarioUbicacionResp(super.getId(), this.nombre, edificio, apartamento, cuarto);
    }

    public Usuario cuartoNull() {
        this.cuarto = null;
        return this;
    }

    public Usuario addCuarto(Cuarto cuarto) {
        setCuarto(cuarto);
        return this;
    }

    public Boolean getUbicar() {
        return ubicar;
    }

    public void setUbicar(Boolean ubicar) {
        this.ubicar = ubicar;
    }

    public Usuario confirmar() {
        this.ubicar = true;
        return this;
    }

    public Usuario desconfirmar() {
        this.ubicar = null;
        return this;
    }
}
