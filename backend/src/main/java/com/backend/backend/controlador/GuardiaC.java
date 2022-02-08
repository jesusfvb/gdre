package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteUpSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaUpSol;
import com.backend.backend.servicios.GuardiaS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardia")
@CrossOrigin("*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GuardiaC {

    private final GuardiaS guardiaS;

    @GetMapping("/residencia")
    private ResponseEntity<List<GuardiaResp>> listarResidencia() {
        return ResponseEntity.ok(guardiaS.listarResidencia());
    }

    @GetMapping("/docente")
    private ResponseEntity<List<GuardiaResp>> listarDocente() {
        return ResponseEntity.ok(guardiaS.listarDocente());
    }

    @PostMapping("/residencia")
    private ResponseEntity<GuardiaResp> salvarResidencia(@RequestBody GuardiaResidenciaSol guardia) {
        return ResponseEntity.ok(guardiaS.salvarResidencia(guardia));
    }

    @PostMapping("/docente")
    private ResponseEntity<GuardiaResp> salvarDocente(@RequestBody GuardiaDocenteSol guardia) {
        return ResponseEntity.ok(guardiaS.salvarDocente(guardia));
    }

    @PutMapping("/residencia")
    private ResponseEntity<GuardiaResp> modificarResidencia(@RequestBody GuardiaResidenciaUpSol guardia) {
        return ResponseEntity.ok(guardiaS.modificarResidencia(guardia));
    }

    @PutMapping("/docente")
    private ResponseEntity<GuardiaResp> modificarDocente(@RequestBody GuardiaDocenteUpSol guardia) {
        return ResponseEntity.ok(guardiaS.modificarDocente(guardia));
    }

    @DeleteMapping
    private ResponseEntity<Integer[]> borrar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(guardiaS.borrar(ids));
    }
}
