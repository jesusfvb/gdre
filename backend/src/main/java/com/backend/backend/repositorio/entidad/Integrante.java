package com.backend.backend.repositorio.entidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

}
