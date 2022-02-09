package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaUpSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.EvaluacionSo;
import com.backend.backend.servicios.CuarteleriaS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuarteleria")
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CuarteleriaC {

    private final CuarteleriaS cuarteleriaS;

    @GetMapping()
    private ResponseEntity<List<CuarteleriaResp>> listar() {
        return ResponseEntity.ok(cuarteleriaS.listar());
    }

    @GetMapping("/{id}")
    private ResponseEntity<List<CuarteleriaResp>> listarPorIdUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(cuarteleriaS.listar(id));
    }

    @PostMapping
    private ResponseEntity<CuarteleriaResp> salvar(@RequestBody CuarteleriaSo cuarteleria) {
        return ResponseEntity.ok(cuarteleriaS.salvar(cuarteleria));
    }

    @PutMapping
    private ResponseEntity<CuarteleriaResp> update(@RequestBody CuarteleriaUpSo cuarteleria) {
        return ResponseEntity.ok(cuarteleriaS.update(cuarteleria));
    }

    @PutMapping("/evaluar")
    private ResponseEntity<CuarteleriaResp> evaluar(@RequestBody EvaluacionSo evaluacion) {
        return ResponseEntity.ok(cuarteleriaS.evaluar(evaluacion));
    }

    @DeleteMapping
    private ResponseEntity<Integer[]> delete(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(cuarteleriaS.delete(ids));
    }
}
