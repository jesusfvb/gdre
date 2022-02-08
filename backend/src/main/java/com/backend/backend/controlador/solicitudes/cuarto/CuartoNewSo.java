package com.backend.backend.controlador.solicitudes.cuarto;

import com.backend.backend.repositorio.entidad.Apartamento;
import com.backend.backend.repositorio.entidad.Cuarto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CuartoNewSo {

    private Integer idApartamento;

    private Integer numero;

    private Integer capacidad;

    @JsonIgnore
    public Cuarto getCuarto() {
        Cuarto cuarto = new Cuarto();
        cuarto.setNumero(this.numero);
        cuarto.setCapacidad(this.capacidad);
        cuarto.setApartamento(new Apartamento(idApartamento));
        return cuarto;
    }

}
