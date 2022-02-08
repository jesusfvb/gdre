package com.backend.backend.controlador.solicitudes.integrantes;

import com.backend.backend.repositorio.entidad.Guardia;
import com.backend.backend.repositorio.entidad.Integrante;
import com.backend.backend.repositorio.entidad.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AsistenciaSo {

    private Integer id;

    private Integrante.Asistencia asistencia;

    @JsonIgnore
    public Integrante getIntegrante(Integrante integrante) {
        integrante.setAsistencia(this.asistencia);
        return integrante;
    }

}
