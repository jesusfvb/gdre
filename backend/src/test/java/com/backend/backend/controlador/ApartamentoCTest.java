package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.servicios.EdificioS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApartamentoCTest {

    @Autowired
    private EdificioS edificioS;

    @Autowired
    private ApartamentoC apartamentoC;

    @Test
    void Test() {
        Integer idEdificio = edificioS.salvar(new EdificioNewSo(1000)).getId();

        //save
        ApartamentoResp apartamentoResp = apartamentoC.salvar(new ApartamentoNewSo(idEdificio, 1000)).getBody();
        assertNotNull(apartamentoResp);

        //save el mismo
        Exception exception = null;
        try {
            apartamentoC.salvar(new ApartamentoNewSo(idEdificio, 1000)).getBody();
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);

        //save datos nulos
        try {
            apartamentoC.salvar(new ApartamentoNewSo(null, null)).getBody();
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);

        //update
        Integer idEdificio2 = edificioS.salvar(new EdificioNewSo(1001)).getId();
        ApartamentoResp apartamentoResp2 = apartamentoC.update(new ApartamentoUpSo(apartamentoResp.getId(), 1001, idEdificio2)).getBody();
        assertNotNull(apartamentoResp2);

        //listar por ID de Edificio
        int cantidad = Objects.requireNonNull(apartamentoC.listarPorIdEdificio(idEdificio2).getBody()).size();
        assertTrue(cantidad >= 1);
        int cantidad2 = Objects.requireNonNull(apartamentoC.listarPorIdEdificio(idEdificio).getBody()).size();
        assertEquals(cantidad2, 0);

        //Delete
        int cantidad3 = Objects.requireNonNull(apartamentoC.borrar(new Integer[]{apartamentoResp.getId()}).getBody()).length;
        assertEquals(cantidad3, 1);

        //Eliminar los edificios
        edificioS.borrar(new Integer[]{idEdificio, idEdificio2});
    }

}