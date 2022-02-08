package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.IntegranteResp;
import com.backend.backend.controlador.respuestas.usuario.UsuarioResp;
import com.backend.backend.controlador.solicitudes.integrantes.AdvertenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.AsistenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.EvaluacionSo;
import com.backend.backend.controlador.solicitudes.integrantes.IntegranteNewSo;
import com.backend.backend.repositorio.IntegranteR;
import com.backend.backend.repositorio.UsuarioR;
import com.backend.backend.repositorio.entidad.Integrante;
import com.backend.backend.servicios.IntegranteS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegranteSI implements IntegranteS {

    @Autowired
    private IntegranteR integranteR;

    @Autowired
    private UsuarioR usuarioR;

    @Override
    public List<IntegranteResp> listarPorGuardia(Integer id) {
        return integranteR.findAllByGuardia_Id(id)
                .parallelStream().map(Integrante::convertir)
                .collect(Collectors.toList());
    }

    @Override
    public IntegranteResp save(IntegranteNewSo integrante) {
        IntegranteResp salida = integranteR.save(new Integrante(integrante)).convertir();
        salida.setParticipante(new UsuarioResp());
        return salida;
    }

    @Override
    public IntegranteResp asistencia(AsistenciaSo asistencia) {
        Integrante integrante = integranteR.getById(asistencia.getId());
        integrante.setAsistencia(asistencia.getAsistencia());
        return integranteR.save(integrante).convertir();
    }

    @Override
    public IntegranteResp evaluacion(EvaluacionSo evaluacion) {
        Integrante integrante = integranteR.getById(evaluacion.getId());
        integrante.setEvaluacion(evaluacion.getEvaluacion());
        return integranteR.save(integrante).convertir();
    }

    @Override
    public IntegranteResp advertencia(AdvertenciaSo advertencia) {
        Integrante integrante = integranteR.getById(advertencia.getId());
        integrante.setAdvertencia(advertencia.getAdvertencia());
        return integranteR.save(integrante).convertir();
    }

    @Override
    public Integer[] delete(Integer[] ids) {
        for (Integer id : ids) {
            integranteR.deleteById(id);
        }
        return ids;
    }

}
