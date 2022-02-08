package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.usuario.UbicarSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioUpSo;
import com.backend.backend.servicios.UsuarioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/usuario")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioC {

    private final UsuarioS usuarioS;

    @GetMapping
    public ResponseEntity<List<UsuarioResp>> listar() {
        return ResponseEntity.ok(usuarioS.listar());
    }

    @GetMapping("/ubicados")
    public ResponseEntity<List<UsuarioUbicacionResp>> listarUbicados() {
        return ResponseEntity.ok(usuarioS.listarUbicados());
    }

    @GetMapping("/no_ubicados")
    public ResponseEntity<List<UsuarioResp>> listarNoUbicados() {
        return ResponseEntity.ok(usuarioS.listarNoUbicados());
    }

    @GetMapping("/por_confirmar")
    public ResponseEntity<List<UsuarioResp>> listarPorConfirmar() {
        return ResponseEntity.ok(usuarioS.listarPorConfirmar());
    }

    @GetMapping("/cuarto")
    public ResponseEntity<List<UsuarioResp>> listarPorIdCuarto(@RequestParam Integer idCuarto) {
        return ResponseEntity.ok(usuarioS.listarPorIdCuarto(idCuarto));
    }

    @PostMapping
    public ResponseEntity<UsuarioResp> save(@RequestBody UsuarioNewSo usuario) {
        return ResponseEntity.ok(usuarioS.save(usuario));
    }

    @PostMapping("/ubicar")
    public ResponseEntity<UsuarioResp> ubicar(@RequestBody UbicarSo ubicarSo) {
        return ResponseEntity.ok(usuarioS.ubicar(ubicarSo));
    }

    @PutMapping("/desubicar")
    public ResponseEntity<Integer[]> desubicar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.desubicar(ids));
    }

    @PutMapping("/confirmar")
    public ResponseEntity<Integer[]> confirmar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.confirmar(ids));
    }

    @PutMapping("/desconfirmar")
    public ResponseEntity<Integer[]> desconfirmar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.desconfirmar(ids));
    }

    @PutMapping
    public ResponseEntity<UsuarioResp> update(@RequestBody UsuarioUpSo usuario) {
        return ResponseEntity.ok(usuarioS.update(usuario));
    }

    @DeleteMapping
    public ResponseEntity<Integer[]> borrar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.borrar(ids));
    }
}
