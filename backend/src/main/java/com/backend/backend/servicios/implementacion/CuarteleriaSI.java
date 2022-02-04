package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.CuarteleriaResp;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.CuarteleriaUpSo;
import com.backend.backend.controlador.solicitudes.cuarteleria.EvaluacionSo;
import com.backend.backend.repositorio.CuarteleriaR;
import com.backend.backend.repositorio.entidad.Cuarteleria;
import com.backend.backend.servicios.CuarteleriaS;
import com.backend.backend.servicios.UsuarioS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CuarteleriaSI implements CuarteleriaS {

    @Autowired
    private CuarteleriaR cuarteleriaR;

    @Autowired
    private UsuarioS usuarioS;

    @Override
    public List<CuarteleriaResp> listar() {

        List<CuarteleriaResp> salida = new LinkedList<>();

        cuarteleriaR.findAll().forEach(cuarteleria -> {
            salida.add(cuarteleria.convertir());
        });

        return salida;
    }

    @Override
    public CuarteleriaResp salvar(CuarteleriaSo cuarteleria) {
        CuarteleriaResp salida = cuarteleriaR.save(new Cuarteleria(cuarteleria)).convertir();
        salida.setNombre(usuarioS.getPorId(cuarteleria.getIdUsuario()).getNombre());
        return salida;
    }

    @Override
    public CuarteleriaResp update(CuarteleriaUpSo cuarteleria) {
        Cuarteleria cuarteleriaP = cuarteleriaR.getById(cuarteleria.getId());
        cuarteleriaP.setFecha(cuarteleria.getFecha());
        return cuarteleriaR.save(cuarteleriaP).convertir();
    }

    @Override
    public CuarteleriaResp evaluar(EvaluacionSo evaluacion) {
        Cuarteleria cuarteleria = cuarteleriaR.getById(evaluacion.getId());
        cuarteleria.setEvaluacion(evaluacion.getEvaluacion());
        return cuarteleriaR.save(cuarteleria).convertir();
    }

    @Override
    public Integer[] delete(Integer[] ids) {
        for (Integer id : ids) {
            cuarteleriaR.deleteById(id);
        }
        return ids;
    }

}
