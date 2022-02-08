package com.backend.backend.controlador.respuestas;

import com.backend.backend.repositorio.entidad.Edificio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EdificioResp {

    private Integer id;

    private Integer numero;

    public EdificioResp(Edificio edificio) {
        this.id = edificio.getId();
        this.numero = edificio.getNumero();
    }
}
