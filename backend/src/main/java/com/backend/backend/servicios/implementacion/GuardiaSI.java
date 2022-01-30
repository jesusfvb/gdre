package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.repositorio.GuardiaR;
import com.backend.backend.servicios.GuardiaS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GuardiaSI implements GuardiaS {

    @Autowired
    private GuardiaR guardiaR;

    @Override
    public List<GuardiaResp> listar() {
        List<GuardiaResp> salida = new LinkedList<>();
        guardiaR.findAll().forEach(guardia -> salida.add(guardia.convertir()));
        return salida;
    }

}
