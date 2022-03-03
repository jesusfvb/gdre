package com.backend.backend.controlador.solicitudes.integrantes;

import com.backend.backend.repositorio.entidad.Integrante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdvertenciaSo {

    private Integer id;

    private String advertencia;

    @JsonIgnore
    public Integrante getIntegrante(Integrante integrante) {
        integrante.setAdvertencia(this.advertencia);
        return integrante;
    }
}
