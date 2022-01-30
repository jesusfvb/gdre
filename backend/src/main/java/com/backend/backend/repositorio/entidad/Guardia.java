package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.GuardiaResp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Guardia extends Entidad {

    private enum Ubicacion {Residencia, Docente}

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "guardia", orphanRemoval = true)
    private List<Integrante> integrantes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "coordinador_id")
    private Usuario coordinador;


    public Guardia() {
    }

    public Usuario getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Usuario coordinador) {
        this.coordinador = coordinador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    @Override
    public GuardiaResp convertir() {
        return null;
    }

}
