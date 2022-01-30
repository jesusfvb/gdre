package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuardiaS {

    public List<GuardiaResp> listar();

}
