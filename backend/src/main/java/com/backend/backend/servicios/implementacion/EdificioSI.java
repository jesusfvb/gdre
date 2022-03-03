package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.EdificioResp;
import com.backend.backend.controlador.solicitudes.edificio.EdificioNewSo;
import com.backend.backend.controlador.solicitudes.edificio.EdificioUpSo;
import com.backend.backend.repositorio.EdificioR;
import com.backend.backend.repositorio.entidad.Edificio;
import com.backend.backend.servicios.EdificioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EdificioSI implements EdificioS {

    private final EdificioR edificioR;

    @Override
    public Edificio getById(Integer id) {
        return edificioR.getById(id);
    }

    @Override
    public List<EdificioResp> listar() {
        return edificioR.findAll().parallelStream().map(EdificioResp::new).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EdificioResp salvar(EdificioNewSo solicitud) {
        return new EdificioResp(edificioR.save(solicitud.getEdificio()));
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
        return new EdificioResp(edificioR.save(solicitud.getEdificio(edificioR.findById(solicitud.getId()).get().getApartamentos())));
    }
}
