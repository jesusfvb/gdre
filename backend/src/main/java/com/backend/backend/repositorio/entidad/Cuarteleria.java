package com.backend.backend.repositorio.entidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cuarteleria extends Entidad {

    @Column(nullable = false)
    private LocalDate fecha;

    @Column
    private String evaluacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
