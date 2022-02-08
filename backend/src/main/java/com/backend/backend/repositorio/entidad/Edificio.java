package com.backend.backend.repositorio.entidad;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Edificio extends Entidad {

    @Column
    private Integer numero;

    @OneToMany(mappedBy = "edificio", cascade = {CascadeType.REMOVE,}, orphanRemoval = true)
    private List<Apartamento> apartamentos = new ArrayList<>();

    public Edificio(Integer id) {
        this.setId(id);
    }
}
