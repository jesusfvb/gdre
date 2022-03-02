package com.backend.backend.controlador.solicitudes.apartamento;

import com.backend.backend.repositorio.entidad.Apartamento;
import com.backend.backend.repositorio.entidad.Edificio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApartamentoUpSo {

    private Integer id;

    private Integer numero;

    private Integer idEdificio;


    @JsonIgnore
    public Apartamento getApartamento() {
        Apartamento apartamento = new Apartamento(id);
        apartamento.setEdificio(new Edificio(this.idEdificio));
        apartamento.setNumero(this.numero);
        return apartamento;
    }

}
