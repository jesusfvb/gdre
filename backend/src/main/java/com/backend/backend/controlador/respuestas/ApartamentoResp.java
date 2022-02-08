package com.backend.backend.controlador.respuestas;

import com.backend.backend.repositorio.entidad.Apartamento;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApartamentoResp {

    private Integer id;

    private Integer numero;

    public ApartamentoResp(Apartamento apartamento) {
        this.id = apartamento.getId();
        this.numero = apartamento.getNumero();
    }
}
