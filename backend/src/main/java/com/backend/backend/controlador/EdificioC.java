package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import com.backend.backend.servicios.EdificioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/edificio")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EdificioC {

    private final EdificioS edificioS;

    @GetMapping
    public ResponseEntity<List<EdificioResp>> listar() {
        return ResponseEntity.ok(edificioS.listar());
    }

    @PostMapping
    public ResponseEntity<EdificioResp> salvar(@RequestBody EdificioNewSo solicitud) {
        return ResponseEntity.ok(edificioS.salvar(solicitud));
    }

    @DeleteMapping
    public ResponseEntity<Integer[]> borrar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(edificioS.borrar(ids));
    }

    @PutMapping
    public ResponseEntity<EdificioResp> update(@RequestBody EdificioUpSo solicitud) {
        return ResponseEntity.ok(edificioS.update(solicitud));
    }
}
