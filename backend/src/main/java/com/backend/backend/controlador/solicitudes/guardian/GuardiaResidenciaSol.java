package com.backend.backend.controlador.solicitudes.guardian;

import com.backend.backend.repositorio.entidad.Guardia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class GuardiaResidenciaSol {

    private LocalDate fecha;

    @JsonIgnore
    public Guardia getGuardia() {
        Guardia guardia = new Guardia();
        guardia.setFecha(this.fecha);
        guardia.setUbicacion(Guardia.Ubicacion.Residencia);
        return guardia;
    }
}
