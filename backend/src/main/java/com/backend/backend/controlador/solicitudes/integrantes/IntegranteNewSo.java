package com.backend.backend.controlador.solicitudes.integrantes;

import com.backend.backend.repositorio.entidad.Guardia;
import com.backend.backend.repositorio.entidad.Integrante;
import com.backend.backend.repositorio.entidad.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IntegranteNewSo {

    private Integer idGuardia;

    private Integer idParticipante;

    @JsonIgnore
    public Integrante getIntegrante(Usuario usuario) {
        Integrante integrante = new Integrante();
        integrante.setAsistencia(Integrante.Asistencia.Pendiente);
        integrante.setEvaluacion(Integrante.Evaluacion.Pendiente);
        integrante.setGuardia(new Guardia(idGuardia));
        integrante.setParticipante(usuario);
        return integrante;
    }

}
