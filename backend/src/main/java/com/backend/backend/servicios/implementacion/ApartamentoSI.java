package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.ApartamentoResp;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoNewSo;
import com.backend.backend.controlador.solicitudes.apartamento.ApartamentoUpSo;
import com.backend.backend.repositorio.ApartamentoR;
import com.backend.backend.repositorio.entidad.Apartamento;
import com.backend.backend.servicios.ApartamentoS;
import com.backend.backend.servicios.EdificioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ApartamentoSI implements ApartamentoS {

    @Autowired
    private ApartamentoR apartamentoR;

    @Autowired
    private EdificioS edificioS;

    @Override
    public List<ApartamentoResp> listarPorIdEdificio(Integer id) {
        List<ApartamentoResp> salida = new LinkedList<>();
        apartamentoR.findAllByEdificio_Id(id).forEach(apartamento -> {
            salida.add(apartamento.convertir());
        });
        return salida;
    }

    @Override
    public ApartamentoResp salvar(ApartamentoNewSo apartamentoNewSo) {
        return apartamentoR.save(new Apartamento(apartamentoNewSo, edificioS.getById(apartamentoNewSo.getIdEdificio()))).convertir();
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
        return apartamentoR.save(new Apartamento(apartamentoUpSo,edificioS.getById(apartamentoUpSo.getIdEdificio()))).convertir();
    }
}
