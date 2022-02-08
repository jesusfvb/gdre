package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Apartamento extends Entidad {

    @Column
    private Integer numero;

    @ManyToOne()
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;

    @OneToMany(mappedBy = "apartamento", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Cuarto> cuartos = new ArrayList<>();

    public Apartamento(Integer id) {
        super.setId(id);
    }
}
