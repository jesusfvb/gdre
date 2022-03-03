package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;
import com.backend.backend.servicios.CuartoS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cuarto")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CuartoC {

    private final CuartoS cuartoS;

    @GetMapping
    public ResponseEntity<List<CuartoResp>> listarPorIdDeApartamento(@RequestParam Integer id) {
        return ResponseEntity.ok(cuartoS.listarPorIdDeApartamento(id));
    }

    @PostMapping
    public ResponseEntity<CuartoResp> salvar(@RequestBody CuartoNewSo cuartoNewSo) {
        return ResponseEntity.ok(cuartoS.salvar(cuartoNewSo));
    }

    @DeleteMapping
    public ResponseEntity<Integer[]> borrar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(cuartoS.borrar(ids));
    }

    @PutMapping
    public ResponseEntity<CuartoResp> update(@RequestBody CuartoUpSo cuartoUpSo) {
        return ResponseEntity.ok(cuartoS.update(cuartoUpSo));
    }

}
