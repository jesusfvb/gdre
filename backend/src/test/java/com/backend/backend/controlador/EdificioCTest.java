package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EdificioCTest {

    @Autowired
    private EdificioC edificioC;

    @Test
    void Test() {
        //save
        EdificioResp edificioResp = edificioC.salvar(new EdificioNewSo(1000)).getBody();
        assertNotNull(edificioResp);

        //save el mismo
        Exception exception = null;
        try {
            edificioC.salvar(new EdificioNewSo(1000));
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);

        //save dato null
        Exception exception2 = null;
        try {
            edificioC.salvar(new EdificioNewSo(null));
        } catch (Exception e) {
            exception2 = e;
        }
        assertNotNull(exception2);

        //listar
        int cantidad = Objects.requireNonNull(edificioC.listar().getBody()).size();
        assertTrue(cantidad >= 1);

        //update
        EdificioResp edificioResp1 = edificioC.update(new EdificioUpSo(edificioResp.getId(), 1001)).getBody();
        assertNotNull(edificioResp1);

        //borrar
        int length = Objects.requireNonNull(edificioC.borrar(new Integer[]{edificioResp.getId(),}).getBody()).length;
        assertEquals(length, 1);
    }

}