package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.IntegranteResp;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaSol;
import com.backend.backend.controlador.solicitudes.integrantes.AdvertenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.AsistenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.EvaluacionSo;
import com.backend.backend.controlador.solicitudes.integrantes.IntegranteNewSo;
import com.backend.backend.controlador.solicitudes.usuario.UsuarioNewSo;
import com.backend.backend.repositorio.entidad.Integrante;
import com.backend.backend.repositorio.entidad.Usuario;
import com.backend.backend.servicios.GuardiaS;
import com.backend.backend.servicios.UsuarioS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IntegranteCTest {

    @Autowired
    private UsuarioS usuarioS;

    @Autowired
    private GuardiaS guardiaS;

    @Autowired
    private IntegranteC integranteC;

    @Test
    void Test() {
        LocalDate date = LocalDate.of(2019, 1, 1);
        Integer idGuardia = guardiaS.salvarResidencia(new GuardiaResidenciaSol(date)).getId();
        Integer idIntegrante = usuarioS.save(new UsuarioNewSo("Test", "test", "test", Usuario.Rol.Estudiante)).getId();

        //save
        IntegranteResp integranteResp = integranteC.save(new IntegranteNewSo(idGuardia, idIntegrante)).getBody();
        assertNotNull(integranteResp);

        //save el mismo
        Exception exception = null;
        try {
            integranteC.save(new IntegranteNewSo(idGuardia, idIntegrante));
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);

        //save datos null
        Exception exception1 = null;
        try {
            integranteC.save(new IntegranteNewSo(null, null));
        } catch (Exception e) {
            exception1 = e;
        }
        assertNotNull(exception1);

        //asistencia
        assertNotNull(integranteC.asistencia(new AsistenciaSo(integranteResp.getId(), Integrante.Asistencia.Presente)).getBody());
        Exception exception2 = null;
        try {
            integranteC.asistencia(new AsistenciaSo(null, null));
        } catch (Exception e) {
            exception2 = e;
        }
        assertNotNull(exception2);

        //evaluacion
        assertNotNull(integranteC.evaluacion(new EvaluacionSo(integranteResp.getId(), Integrante.Evaluacion.Bien)).getBody());

        Exception exception3 = null;
        try {
            integranteC.evaluacion(new EvaluacionSo(null, null));
        } catch (Exception e) {
            exception3 = e;
        }
        assertNotNull(exception3);

        //advertencia
        assertNotNull(integranteC.advertencia(new AdvertenciaSo(integranteResp.getId(), "Test")).getBody());

        Exception exception4 = null;
        try {
            integranteC.advertencia(new AdvertenciaSo(null, null));
        } catch (Exception e) {
            exception4 = e;
        }
        assertNotNull(exception4);

        //listar
        int cantidad1 = Objects.requireNonNull(integranteC.listarPorIdGuardia(idGuardia).getBody()).size();
        assertTrue(cantidad1 >= 1);

        //delete
        int cantidad = Objects.requireNonNull(integranteC.delete(new Integer[]{integranteResp.getId()}).getBody()).length;
        assertEquals(cantidad, 1);

        usuarioS.borrar(new Integer[]{idIntegrante});
        guardiaS.borrar(new Integer[]{idGuardia});
    }

}