package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;
import com.backend.backend.repositorio.ApartamentoR;
import com.backend.backend.repositorio.entidad.Apartamento;
import com.backend.backend.servicios.ApartamentoS;
import com.backend.backend.servicios.EdificioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApartamentoSI implements ApartamentoS {

    private final ApartamentoR apartamentoR;

    private final EdificioS edificioS;

    @Override
    public Apartamento geById(Integer idApartamento) {
        return apartamentoR.getById(idApartamento);
    }

    @Override
    public List<ApartamentoResp> listarPorIdEdificio(Integer id) {
        return apartamentoR.findAllByEdificio_Id(id).parallelStream().map(ApartamentoResp::new).collect(Collectors.toList());
    }

    @Override
    public ApartamentoResp salvar(ApartamentoNewSo apartamentoNewSo) {
        return new ApartamentoResp(apartamentoR.save(apartamentoNewSo.getApartamento()));
    }

    @Override
    public Integer[] borrar(Integer[] ids) {
        for (Integer id : ids) {
            apartamentoR.deleteById(id);
        }
        return ids;
    }

    @Override
    public ApartamentoResp update(ApartamentoUpSo apartamentoUpSo) {
        return new ApartamentoResp(apartamentoR.save(apartamentoUpSo.getApartamento(apartamentoR.getById(apartamentoUpSo.getId()))));
    }
}
