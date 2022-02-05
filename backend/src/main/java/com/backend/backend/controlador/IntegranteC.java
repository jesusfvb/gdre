package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.IntegranteResp;
import com.backend.backend.controlador.solicitudes.integrantes.AdvertenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.AsistenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.EvaluacionSo;
import com.backend.backend.controlador.solicitudes.integrantes.IntegranteNewSo;
import com.backend.backend.servicios.IntegranteS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/integrante")
@CrossOrigin
public class IntegranteC {

    @Autowired
    private IntegranteS integranteS;

    @GetMapping("/guardia/{id}")
    private ResponseEntity<List<IntegranteResp>> listarPorIdGuardia(@PathVariable Integer id) {
        return ResponseEntity.ok(integranteS.listarPorGuardia(id));
    }

    @PostMapping
    private ResponseEntity<IntegranteResp> save(@RequestBody IntegranteNewSo integrante) {
        return ResponseEntity.ok(integranteS.save(integrante));
    }

    @DeleteMapping
    private ResponseEntity<Integer[]> delete(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(integranteS.delete(ids));
    }

    @PutMapping("/asistencia")
    private ResponseEntity<IntegranteResp> asistencia(@RequestBody AsistenciaSo asistencia) {
        return ResponseEntity.ok(integranteS.asistencia(asistencia));
    }

    @PutMapping("/evaluacion")
    private ResponseEntity<IntegranteResp> evaluacion(@RequestBody EvaluacionSo evaluacion) {
        return ResponseEntity.ok(integranteS.evaluacion(evaluacion));
    }

    @PutMapping("/advertencia")
    private ResponseEntity<IntegranteResp> advertencia(@RequestBody AdvertenciaSo advertencia) {
        return ResponseEntity.ok(integranteS.advertencia(advertencia));
    }

}
