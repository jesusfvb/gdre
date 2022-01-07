package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cuarto extends Entidad {

    @Column
    private Integer numero;

    @Column
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "apartamento_id")
    private Apartamento apartamento;

    @OneToMany(mappedBy = "cuarto")
    private List<Usuario> usuarios = new ArrayList<>();

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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
