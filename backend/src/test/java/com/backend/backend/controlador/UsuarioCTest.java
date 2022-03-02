package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UbicarSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioUpSo;
import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.ApartamentoS;
import com.backend.backend.servicios.CuartoS;
import com.backend.backend.servicios.EdificioS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioCTest {
    @Autowired
    private EdificioS edificioS;

    @Autowired
    private ApartamentoS apartamentoS;

    @Autowired
    private CuartoS cuartoS;

    @Autowired
    private UsuarioC usuarioC;

    @Test
    void Test() {
        Integer idEdificio = edificioS.salvar(new EdificioNewSo(1000)).getId();
        Integer idApartamento = apartamentoS.salvar(new ApartamentoNewSo(idEdificio, 1000)).getId();
        Integer idCuarto = cuartoS.salvar(new CuartoNewSo(idApartamento, 1000, 3)).getId();

        //save
        UsuarioResp usuarioResp = usuarioC.save(new UsuarioNewSo("Test", "test", "test", Usuario.Rol.Estudiante)).getBody();
        assertNotNull(usuarioResp);
        UsuarioResp usuarioResp2 = usuarioC.save(new UsuarioNewSo("Test2", "test2", "test2", Usuario.Rol.Profesor)).getBody();
        assertNotNull(usuarioResp2);
        UsuarioResp usuarioResp3 = usuarioC.save(new UsuarioNewSo("Test3", "test3", "test3", Usuario.Rol.Usuario)).getBody();
        assertNotNull(usuarioResp3);

        //save igual
        Exception exception = null;
        try {
            usuarioC.save(new UsuarioNewSo("Test", "test", "test", Usuario.Rol.Estudiante));
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);

        //save null
        Exception exception1 = null;
        try {
            usuarioC.save(new UsuarioNewSo(null, null, null, null));
        } catch (Exception e) {
            exception1 = e;
        }
        assertNotNull(exception1);

        //modificar
        assertNotNull(usuarioC.update(new UsuarioUpSo(usuarioResp3.getId(), "Test4", "Test4", "Test4", Usuario.Rol.Estudiante)).getBody());

        //confirmar
        assertEquals(Objects.requireNonNull(usuarioC.confirmar(new Integer[]{usuarioResp.getId(), usuarioResp3.getId()}).getBody()).length, 2);

        //desconfirmar
        assertEquals(Objects.requireNonNull(usuarioC.desconfirmar(new Integer[]{usuarioResp3.getId()}).getBody()).length, 1);

        //ubicar
        assertNotNull(usuarioC.ubicar(new UbicarSo(usuarioResp.getId(), idCuarto)).getBody());
        Exception exception2 = null;
        try {
            usuarioC.ubicar(new UbicarSo(usuarioResp3.getId(), idCuarto));
        } catch (Exception e) {
            exception2 = e;
        }
        assertNotNull(exception2);

        //desubicar
        assertEquals(Objects.requireNonNull(usuarioC.desubicar(new Integer[]{usuarioResp.getId()}).getBody()).length, 1);

        //listar por confirmar
        assertTrue(Objects.requireNonNull(usuarioC.listarPorConfirmar().getBody()).size() >= 1);

        //listar profesor
        assertTrue(Objects.requireNonNull(usuarioC.listarProfesor().getBody()).size() >= 1);

        //listar por ID Cuarto
        assertTrue(Objects.requireNonNull(usuarioC.listarPorIdCuarto(idCuarto).getBody()).size() >= 0);

        //listar no ubicados
        assertTrue(Objects.requireNonNull(usuarioC.listarNoUbicados().getBody()).size() >= 1);

        // listar ubicados
        assertTrue(Objects.requireNonNull(usuarioC.listarUbicados().getBody()).size() >= 0);

        //listar todos
        int cantidad = Objects.requireNonNull(usuarioC.listar().getBody()).size();
        assertTrue(cantidad >= 3);

        //borrar
        int length = Objects.requireNonNull(usuarioC.borrar(new Integer[]{usuarioResp.getId(), usuarioResp2.getId(), usuarioResp3.getId()}).getBody()).length;
        assertEquals(length, 3);

        edificioS.borrar(new Integer[]{idEdificio});
    }

}