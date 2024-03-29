package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.CuartoResp;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoNewSo;
import com.backend.backend.controlador.solicitudes.cuarto.CuartoUpSo;
import com.backend.backend.repositorio.CuartoR;
import com.backend.backend.repositorio.entidad.Cuarto;
import com.backend.backend.servicios.ApartamentoS;
import com.backend.backend.servicios.CuartoS;
import com.backend.backend.servicios.UsuarioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class CuartoSI implements CuartoS {

    private final CuartoR cuartoR;

    private final UsuarioS usuarioS;

    @Override
    public Cuarto getById(Integer idCuarto) {
        return cuartoR.findById(idCuarto).get();
    }

    @Override
    public List<CuartoResp> listarPorIdDeApartamento(Integer id) {
        return cuartoR.findAllByApartamento_Id(id).parallelStream().map(CuartoResp::new).collect(Collectors.toList());
    }

    @Override
    public CuartoResp salvar(CuartoNewSo cuartoNewSo) {
        if (cuartoR.existsByNumeroAndApartamento_Id(cuartoNewSo.getNumero(), cuartoNewSo.getIdApartamento())) {
            throw new RuntimeException("Cuarto ya existe");
        }
        return new CuartoResp(cuartoR.save(cuartoNewSo.getCuarto()));
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
        Cuarto cuarto = cuartoR.findById(cuartoUpSo.getId()).get();
        if (cuartoUpSo.getCapacidad() < cuarto.getUsuarios().size()) {
            throw new RuntimeException("No se puede modificar cuarto");
        }
        return new CuartoResp(cuartoR.save(cuartoUpSo.getCuarto(cuarto)));
    }
}
