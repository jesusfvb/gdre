package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaSol;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Guardia extends Entidad {

    public enum Ubicacion {Residencia, Docente}

    @Column
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

    public Guardia(Integer idGuardia) {
        this.setId(idGuardia);
    }

    public Guardia(GuardiaResidenciaSol guardia) {
        this.fecha = guardia.getFecha();
        this.coordinador = null;
        this.ubicacion = Ubicacion.Residencia;
    }

    public Guardia(GuardiaDocenteSol guardia) {
        this.fecha = guardia.getFecha();
        this.coordinador = new Usuario(guardia.getIdCoordinador());
        this.ubicacion = Ubicacion.Docente;
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

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public GuardiaResp convertir() {
        return new GuardiaResp(this.getId(), this.fecha, (coordinador != null) ? null : null);
    }

}
