package com.backend.backend.controlador.solicitudes.guardian;

import com.backend.backend.repositorio.entidad.Guardia;
import com.backend.backend.repositorio.entidad.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GuardiaDocenteSol {

    private Integer idCoordinador;

    private LocalDate fecha;

    @JsonIgnore
    public Guardia getGuardia(Usuario coordinador) {
        Guardia guardia = new Guardia();
        guardia.setFecha(this.fecha);
        guardia.setCoordinador(coordinador);
        guardia.setUbicacion(Guardia.Ubicacion.Docente);
        return guardia;
    }
}
