package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;
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

    public Cuarto(Integer id) {
        super.setId(id);
    }
}
