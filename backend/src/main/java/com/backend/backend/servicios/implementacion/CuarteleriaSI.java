package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaUpSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.EvaluacionSo;
import com.backend.backend.repositorio.CuarteleriaR;
import com.backend.backend.repositorio.entidad.Cuarteleria;
import com.backend.backend.servicios.CuarteleriaS;
import com.backend.backend.servicios.UsuarioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CuarteleriaSI implements CuarteleriaS {

    private final CuarteleriaR cuarteleriaR;

    private final UsuarioS usuarioS;

    @Override
    public List<CuarteleriaResp> listar() {
        return cuarteleriaR.findAll().parallelStream().map(CuarteleriaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<CuarteleriaResp> listar(Integer id) {
        return cuarteleriaR.findAllByUsuario_Id(id).parallelStream().map(CuarteleriaResp::new).collect(Collectors.toList());
    }

    @Override
    public CuarteleriaResp salvar(CuarteleriaSo cuarteleria) {
        if (cuarteleriaR.existsByFechaAndUsuario_Id(cuarteleria.getFecha(), cuarteleria.getIdUsuario())) {
            throw new RuntimeException("Ya existe la cuarteleria");
        }
        return new CuarteleriaResp(cuarteleriaR.save(cuarteleria.getCuarteleria(usuarioS.getPorId(cuarteleria.getIdUsuario()))));
    }

    @Override
    public CuarteleriaResp update(CuarteleriaUpSo cuarteleria) {
        Cuarteleria cuarteleria1 = cuarteleria.getCuarteleria(cuarteleriaR.findById(cuarteleria.getId()).get());
        if (cuarteleriaR.existsByFechaAndUsuario_Id(cuarteleria1.getFecha(), cuarteleria1.getUsuario().getId())) {
            throw new RuntimeException("Ya existe la cuarteleria");
        }
        return new CuarteleriaResp(cuarteleriaR.save(cuarteleria1));
    }

    @Override
    public CuarteleriaResp evaluar(EvaluacionSo evaluacion) {
        if (evaluacion.getEvaluacion().equals(null)) {
            throw new RuntimeException("Dato invalido");
        }
        return new CuarteleriaResp(cuarteleriaR.save(evaluacion.getCuarteleria(cuarteleriaR.findById(evaluacion.getId()).get())));
    }

    @Override
    public Integer[] delete(Integer[] ids) {
        for (Integer id : ids) {
            cuarteleriaR.deleteById(id);
        }
        return ids;
    }

}
