package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import com.backend.backend.repositorio.EdificioR;
import com.backend.backend.repositorio.entidad.Edificio;
import com.backend.backend.servicios.EdificioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EdificioSI implements EdificioS {

    @Autowired
    private EdificioR edificioR;

    @Override
    public List<EdificioResp> listar() {
        List<EdificioResp> listaSalida = new LinkedList<>();
        edificioR.findAll().forEach((edifico) -> listaSalida.add(edifico.convertir()));
        return listaSalida;
    }

    @Override
    public EdificioResp salvar(EdificioNewSo solicitud) {
        return edificioR.save(new Edificio(solicitud)).convertir();
    }


    @Override
    public Integer[] borrar(Integer[] ids) {
        for (Integer id : ids) {
            edificioR.deleteById(id);
        }
        return ids;
    }

    @Override
    public EdificioResp update(EdificioUpSo solicitud) {
        return edificioR.save(new Edificio(solicitud)).convertir();
    }
}
