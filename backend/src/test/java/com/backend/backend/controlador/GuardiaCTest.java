package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteUpSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaUpSol;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.UsuarioS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuardiaCTest {

    @Autowired
    private UsuarioS usuarioS;

    @Autowired
    private GuardiaC guardiaC;

    @Test
    void Test() {

        int idUsuario = usuarioS.save(new UsuarioNewSo("Test", "test", "test", Usuario.Rol.Profesor)).getId();
        LocalDate date = LocalDate.of(2019, 1, 1);

        int idUsuario2 = usuarioS.save(new UsuarioNewSo("Test2", "test2", "test2", Usuario.Rol.Profesor)).getId();
        LocalDate date2 = LocalDate.of(2020, 1, 1);

        //Docente
        //save
        GuardiaResp guardiaDocenteResp = guardiaC.salvarDocente(new GuardiaDocenteSol(idUsuario, date)).getBody();
        assertNotNull(guardiaDocenteResp);

        //save el mismo
        Exception exception = null;
        try {
            guardiaC.salvarDocente(new GuardiaDocenteSol(idUsuario, date));
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        //save null
        Exception exception2 = null;
        try {
            guardiaC.salvarDocente(new GuardiaDocenteSol(null, null));
        } catch (Exception e) {
            exception2 = e;
        }
        assertNotNull(exception2);

        //modificar
        GuardiaResp guardiaDocenteResp2 = guardiaC.modificarDocente(new GuardiaDocenteUpSol(guardiaDocenteResp.getId(), idUsuario2, date2)).getBody();
        assertNotNull(guardiaDocenteResp2);

        //listar
        int cantidad2 = Objects.requireNonNull(guardiaC.listarDocente().getBody()).size();
        assertTrue(cantidad2 >= 1);

        //listar por ID de profesor
        int cantidad3 = Objects.requireNonNull(guardiaC.listarDocentePorIProfesor(idUsuario2).getBody()).size();
        assertTrue(cantidad3 >= 1);

        //listar por ID de participante
        int cantidad4 = Objects.requireNonNull(guardiaC.listarDocentePorIdParticipante(idUsuario).getBody()).size();
        assertTrue(cantidad4 >= 0);


        //Residencia
        //save
        GuardiaResp guardiaResidenciaResp = guardiaC.salvarResidencia(new GuardiaResidenciaSol(date)).getBody();
        assertNotNull(guardiaResidenciaResp);

        //save el mismo
        Exception exception1;
        try {
            guardiaC.salvarResidencia(new GuardiaResidenciaSol(date)).getBody();
        } catch (Exception e) {
            exception1 = e;
        }

        //save el mismo
        Exception exception3;
        try {
            guardiaC.salvarResidencia(new GuardiaResidenciaSol(null)).getBody();
        } catch (Exception e) {
            exception3 = e;
        }

        //modificar
        GuardiaResp guardiaResidenciaResp2 = guardiaC.modificarResidencia(new GuardiaResidenciaUpSol(guardiaResidenciaResp.getId(), date2)).getBody();
        assertNotNull(guardiaResidenciaResp2);

        //listar
        int cantidad1 = Objects.requireNonNull(guardiaC.listarResidencia().getBody()).size();
        assertTrue(cantidad1 >= 1);

        //listar por ID participante
        int cantidad5 = Objects.requireNonNull(guardiaC.listarResidenciaPorIdParticipante(idUsuario).getBody()).size();
        assertTrue(cantidad5 >= 0);

        //listar todos
        int cantidad = Objects.requireNonNull(guardiaC.listar().getBody()).size();
        assertTrue(cantidad >= 2);

        //borrar
        int length = Objects.requireNonNull(guardiaC.borrar(new Integer[]{guardiaDocenteResp.getId(), guardiaResidenciaResp.getId()}).getBody()).length;
        assertEquals(length, 2);

        usuarioS.borrar(new Integer[]{idUsuario, idUsuario2});
    }

}