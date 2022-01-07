package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;
import com.backend.backend.repositorio.CuartoR;
import com.backend.backend.repositorio.entidad.Cuarto;
import com.backend.backend.servicios.ApartamentoS;
import com.backend.backend.servicios.CuartoS;
import com.backend.backend.servicios.UsuarioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CuartoSI implements CuartoS {

    @Autowired
    private CuartoR cuartoR;

    @Autowired
    private ApartamentoS apartamentoS;

    @Autowired
    private UsuarioS usuarioS;

    @Override
    public Cuarto getById(Integer idCuarto) {
        return cuartoR.getById(idCuarto);
    }

    @Override
    public List<CuartoResp> listarPorIdDeApartamento(Integer id) {
        List<CuartoResp> salida = new LinkedList<>();
        cuartoR.findAllByApartamento_Id(id).forEach(cuarto -> {
            salida.add(cuarto.convertir());
        });
        return salida;
    }

    @Override
    public CuartoResp salvar(CuartoNewSo cuartoNewSo) {
        return cuartoR.save(new Cuarto(cuartoNewSo, apartamentoS.geById(cuartoNewSo.getIdApartamento()))).convertir();
    }

    @Override
    public Integer[] borrar(Integer[] ids) {
        for (Integer id : ids) {
            usuarioS.quitarCuarto(id);
            cuartoR.deleteById(id);
        }
        return ids;
    }

    @Override
    public CuartoResp update(CuartoUpSo cuartoUpSo) {
        return cuartoR.save(new Cuarto(cuartoUpSo, apartamentoS.geById(cuartoUpSo.getIdApartamento()))).convertir();
    }
}
