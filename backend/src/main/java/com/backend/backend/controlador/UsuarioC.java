package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioUbicacionResp;
import com.backend.backend.controlador.solicitudes.UbicarSo;
import com.backend.backend.servicios.UsuarioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/usuario")
public class UsuarioC {

    @Autowired
    private UsuarioS usuarioS;

    @GetMapping("/ubicados")
    private ResponseEntity<List<UsuarioUbicacionResp>> listarUbicados() {
        return ResponseEntity.ok(usuarioS.listarUbicados());
    }

    @GetMapping("/no_ubicados")
    private ResponseEntity<List<UsuarioResp>> listarNoUbicados() {
        return ResponseEntity.ok(usuarioS.listarNoUbicados());
    }

    @GetMapping("/por_confirmar")
    private ResponseEntity<List<UsuarioResp>> listarPorConfirmar() {
        return ResponseEntity.ok(usuarioS.listarPorConfirmar());
    }

    @GetMapping("/cuarto")
    private ResponseEntity<List<UsuarioResp>> listarPorIdCuarto(@RequestParam Integer idCuarto) {
        return ResponseEntity.ok(usuarioS.listarPorIdCuarto(idCuarto));
    }

    @PostMapping("/ubicar")
    private ResponseEntity<UsuarioResp> ubicar(@RequestBody UbicarSo ubicarSo) {
        return ResponseEntity.ok(usuarioS.ubicar(ubicarSo));
    }

    @PutMapping("/desubicar")
    private ResponseEntity<Integer[]> desubicar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.desubicar(ids));
    }

    @PutMapping("/confirmar")
    private ResponseEntity<Integer[]> confirmar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.confirmar(ids));
    }

    @PutMapping("/desconfirmar")
    private ResponseEntity<Integer[]> desconfirmar(@RequestBody Integer[] ids) {
        return ResponseEntity.ok(usuarioS.desconfirmar(ids));
    }
}
