package com.backend.backend.controlador.solicitudes.guardian;

import com.backend.backend.repositorio.entidad.Guardia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GuardiaResidenciaUpSol {

    private Integer id;

    private LocalDate fecha;

    @JsonIgnore
    public Guardia getGuardia(Guardia guardia) {
        guardia.setFecha(this.fecha);
        guardia.setUbicacion(Guardia.Ubicacion.Residencia);
        return guardia;
    }
}
