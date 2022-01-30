package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.servicios.GuardiaS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guardia")
@CrossOrigin("*")
public class GuardiaC {

    @Autowired
    private GuardiaS guardiaS;

    @GetMapping
    private ResponseEntity<List<GuardiaResp>> listar() {
        return ResponseEntity.ok(guardiaS.listar());
    }

}
