package com.backend.backend.controlador.solicitudes.edificio;

import com.backend.backend.repositorio.entidad.Edificio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EdificioNewSo {

    Integer numero;

    @JsonIgnore
    public Edificio getEdificio() {
        Edificio edificio = new Edificio();
        edificio.setNumero(numero);
        return edificio;
    }

}
