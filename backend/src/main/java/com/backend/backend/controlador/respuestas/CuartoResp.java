package com.backend.backend.controlador.respuestas;

import com.backend.backend.repositorio.entidad.Cuarto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CuartoResp {

    private Integer id;

    private Integer numero;

    private Integer capacidad;

    public CuartoResp(Cuarto cuarto) {
        this.id = cuarto.getId();
        this.numero = cuarto.getNumero();
        this.capacidad = cuarto.getCapacidad();
    }
}
