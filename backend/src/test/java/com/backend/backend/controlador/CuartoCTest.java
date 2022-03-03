package com.backend.backend.controlador;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.servicios.ApartamentoS;
import com.backend.backend.servicios.EdificioS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CuartoCTest {

    @Autowired
    private EdificioS edificioS;

    @Autowired
    private ApartamentoS apartamentoS;

    @Autowired
    private CuartoC cuartoC;

    @Test
    void Test() {

        Integer idEdificio = edificioS.salvar(new EdificioNewSo(1000)).getId();
        Integer idApartamento = apartamentoS.salvar(new ApartamentoNewSo(idEdificio, 1000)).getId();

        //save
        CuartoResp cuartoResp = cuartoC.salvar(new CuartoNewSo(idApartamento, 1000, 3)).getBody();
        assertNotNull(cuartoResp);

        //save el mismo
        Exception exception = null;
        try {
            cuartoC.salvar(new CuartoNewSo(idApartamento, 1000, 3));
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);

        //save el datos null
        Exception exception2 = null;
        try {
            cuartoC.salvar(new CuartoNewSo(null, null, null));
        } catch (Exception e) {
            exception2 = e;
        }
        assertNotNull(exception2);

        //update
        CuartoResp cuartoResp1 = cuartoC.update(new CuartoUpSo(cuartoResp.getId(), idApartamento, 1001, 5)).getBody();
        assertNotNull(cuartoResp1);

        //listar
        int cantidad1 = Objects.requireNonNull(cuartoC.listarPorIdDeApartamento(idApartamento).getBody()).size();
        assertTrue(cantidad1 >= 1);

        //borrar
        int cantidad = Objects.requireNonNull(cuartoC.borrar(new Integer[]{cuartoResp.getId()}).getBody()).length;
        assertEquals(cantidad, 1);

        edificioS.borrar(new Integer[]{idEdificio});
    }
}