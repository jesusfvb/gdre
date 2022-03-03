package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaUpSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.EvaluacionSo;
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
class CuarteleriaCTest {

    @Autowired
    private UsuarioS usuarioS;

    @Autowired
    private CuarteleriaC cuarteleriaC;

    @Test
    void Test() {

        int idUsuario = usuarioS.save(new UsuarioNewSo("Test", "test", "test", Usuario.Rol.Usuario)).getId();
        LocalDate date = LocalDate.now();

        //save
        CuarteleriaResp cuartoResp = cuarteleriaC.salvar(new CuarteleriaSo(idUsuario, date)).getBody();
        assertNotNull(cuartoResp);

        //save el mismo
        Exception exception = null;
        try {
            cuarteleriaC.salvar(new CuarteleriaSo(idUsuario, date));
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        //save el mismo
        Exception exception2 = null;
        try {
            cuarteleriaC.salvar(new CuarteleriaSo(null, null));
        } catch (Exception e) {
            exception2 = e;
        }
        assertNotNull(exception2);

        //update
        CuarteleriaResp cuartoResp2 = cuarteleriaC.update(new CuarteleriaUpSo(cuartoResp.getId(), LocalDate.of(2020, 1, 1))).getBody();
        assertNotNull(cuartoResp2);

        //evaluar
        CuarteleriaResp cuartoResp3 = cuarteleriaC.evaluar(new EvaluacionSo(cuartoResp.getId(), "Bien")).getBody();
        assertNotNull(cuartoResp3);

        //evaluar datos null
        Exception exception1 = null;
        try {
            CuarteleriaResp cuartoResp4 = cuarteleriaC.evaluar(new EvaluacionSo(null, null)).getBody();
        } catch (Exception e) {
            exception1 = e;
        }
        assertNotNull(exception1);

        int cantidad1 = Objects.requireNonNull(cuarteleriaC.listar().getBody()).size();
        assertTrue(cantidad1 >= 1);

        int cantidad2 = Objects.requireNonNull(cuarteleriaC.listarPorIdUsuario(idUsuario).getBody()).size();
        assertTrue(cantidad2 >= 1);

        //delete
        int cantidad = Objects.requireNonNull(cuarteleriaC.delete(new Integer[]{cuartoResp.getId()}).getBody()).length;
        assertEquals(cantidad, 1);

        usuarioS.borrar(new Integer[]{idUsuario});
    }

}