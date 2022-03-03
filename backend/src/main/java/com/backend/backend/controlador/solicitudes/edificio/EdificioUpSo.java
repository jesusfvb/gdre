package com.backend.backend.controlador.solicitudes.edificio;

import com.backend.backend.repositorio.entidad.Apartamento;
import com.backend.backend.repositorio.entidad.Edificio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EdificioUpSo {

    private Integer id;

    private Integer numero;

    @JsonIgnore
    public Edificio getEdificio(List<Apartamento> apartamentoList) {
        Edificio edificio = new Edificio(this.numero, apartamentoList);
        edificio.setId(this.id);
        return edificio;
    }
}
