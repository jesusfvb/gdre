package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;
import com.backend.backend.servicios.ApartamentoS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/apartamento")
public class ApartamentoC {

    @Autowired
    private ApartamentoS apartamentoS;

    @GetMapping
    private ResponseEntity<List<ApartamentoResp>> listarPorIdEdificio(@RequestParam Integer id) {
        return ResponseEntity.ok(apartamentoS.listarPorIdEdificio(id));
    }

    @PostMapping
    private ResponseEntity<ApartamentoResp> salvar(@RequestBody ApartamentoNewSo apartamentoNewSo) {
        return ResponseEntity.ok(apartamentoS.salvar(apartamentoNewSo));
    }

    @DeleteMapping
    private ResponseEntity<Integer[]> borrar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(apartamentoS.borrar(ids));
    }

    @PutMapping
    private ResponseEntity<ApartamentoResp> update(@RequestBody ApartamentoUpSo apartamentoUpSo) {
        return ResponseEntity.ok(apartamentoS.update(apartamentoUpSo));
    }
}
