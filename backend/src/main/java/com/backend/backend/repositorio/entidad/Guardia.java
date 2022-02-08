package com.backend.backend.repositorio.entidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Guardia extends Entidad {

    public enum Ubicacion {Residencia, Docente;}

    @Column
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "guardia", orphanRemoval = true)
    private List<Integrante> integrantes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "coordinador_id")
    private Usuario coordinador;

    public Guardia(Integer id) {
        super.setId(id);
    }

}
