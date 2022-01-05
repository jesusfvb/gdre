package com.backend.backend.controlador.solicitudes.apartamento;

import com.backend.backend.repositorio.entidad.Edificio;
import com.backend.backend.servicios.EdificioS;
import com.backend.backend.servicios.implementacion.EdificioSI;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApartamentoNewSo {

    Integer idEdificio;

    Integer numero;

    public ApartamentoNewSo() {
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getIdEdificio() {
        return idEdificio;
    }

}
