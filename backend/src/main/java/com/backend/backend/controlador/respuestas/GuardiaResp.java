package com.backend.backend.controlador.respuestas;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.repositorio.entidad.Guardia;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class GuardiaResp {

    private Integer id;

    private UsuarioResp coordinador;

    private LocalDate fecha;

    public GuardiaResp(Guardia guardia) {
        this.id = guardia.getId();
        if (guardia.getCoordinador() != null) {
            this.coordinador = new UsuarioResp(guardia.getCoordinador());
        }
        this.fecha = guardia.getFecha();
    }

}
