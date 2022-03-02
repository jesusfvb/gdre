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

    @GetMapping()
    public ResponseEntity<List<GuardiaResp>> listar() {
        return ResponseEntity.ok(guardiaS.listar());
    }

    @GetMapping("/residencia")
    public ResponseEntity<List<GuardiaResp>> listarResidencia() {
        return ResponseEntity.ok(guardiaS.listarResidencia());
    }

    @GetMapping("/residencia/{id}")
    public ResponseEntity<List<GuardiaResp>> listarResidenciaPorIdParticipante(@PathVariable Integer id) {
        return ResponseEntity.ok(guardiaS.listarResidencia(id));
    }

    @GetMapping("/docente")
    public ResponseEntity<List<GuardiaResp>> listarDocente() {
        return ResponseEntity.ok(guardiaS.listarDocente());
    }

    @GetMapping("/docente/{id}")
    public ResponseEntity<List<GuardiaResp>> listarDocentePorIdParticipante(@PathVariable Integer id) {
        return ResponseEntity.ok(guardiaS.listarDocente(id));
    }

    @GetMapping("/docente/profesor/{id}")
    public ResponseEntity<List<GuardiaResp>> listarDocentePorIProfesor(@PathVariable Integer id) {
        return ResponseEntity.ok(guardiaS.listarDocenteProfesor(id));
    }

    @PostMapping("/residencia")
    public ResponseEntity<GuardiaResp> salvarResidencia(@RequestBody GuardiaResidenciaSol guardia) {
        return ResponseEntity.ok(guardiaS.salvarResidencia(guardia));
    }

    @PostMapping("/docente")
    public ResponseEntity<GuardiaResp> salvarDocente(@RequestBody GuardiaDocenteSol guardia) {
        return ResponseEntity.ok(guardiaS.salvarDocente(guardia));
    }

    @PutMapping("/residencia")
    public ResponseEntity<GuardiaResp> modificarResidencia(@RequestBody GuardiaResidenciaUpSol guardia) {
        return ResponseEntity.ok(guardiaS.modificarResidencia(guardia));
    }

    @PutMapping("/docente")
    public ResponseEntity<GuardiaResp> modificarDocente(@RequestBody GuardiaDocenteUpSol guardia) {
        return ResponseEntity.ok(guardiaS.modificarDocente(guardia));
    }

    @DeleteMapping
    public ResponseEntity<Integer[]> borrar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(guardiaS.borrar(ids));
    }
}
