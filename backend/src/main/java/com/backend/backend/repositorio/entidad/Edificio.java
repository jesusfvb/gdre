package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Edificio extends Entidad {

    @Column(unique = true, nullable = false)
    private Integer numero;

    @OneToMany(mappedBy = "edificio", cascade = {CascadeType.REMOVE,}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Apartamento> apartamentos = new ArrayList<>();

    public Edificio(Integer id) {
        this.setId(id);
    }
}
