package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.IntegranteResp;
import com.backend.backend.controlador.solicitudes.integrantes.AdvertenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.AsistenciaSo;
import com.backend.backend.controlador.solicitudes.integrantes.EvaluacionSo;
import com.backend.backend.controlador.solicitudes.integrantes.IntegranteNewSo;
import com.backend.backend.repositorio.IntegranteR;
import com.backend.backend.repositorio.UsuarioR;
import com.backend.backend.servicios.IntegranteS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IntegranteSI implements IntegranteS {

    private final IntegranteR integranteR;

    private final UsuarioR usuarioR;

    @Override
    public List<IntegranteResp> listarPorGuardia(Integer id) {
        return integranteR.findAllByGuardia_Id(id).parallelStream().map(IntegranteResp::new).collect(Collectors.toList());
    }

    @Override
    public IntegranteResp save(IntegranteNewSo integrante) {
        if (integranteR.existsByGuardia_IdAndParticipante_Id(integrante.getIdGuardia(), integrante.getIdParticipante())) {
            throw new RuntimeException("El Integrante ya existe");
        }
        return new IntegranteResp(integranteR.save(integrante.getIntegrante(usuarioR.findById(integrante.getIdParticipante()).get())));
    }

    @Override
    public IntegranteResp asistencia(AsistenciaSo asistencia) {
        return new IntegranteResp(integranteR.save(asistencia.getIntegrante(integranteR.findById(asistencia.getId()).get())));
    }

    @Override
    public IntegranteResp evaluacion(EvaluacionSo evaluacion) {
        return new IntegranteResp(integranteR.save(evaluacion.getIntegrante(integranteR.findById(evaluacion.getId()).get())));
    }

    @Override
    public IntegranteResp advertencia(AdvertenciaSo advertencia) {
        if (advertencia.getAdvertencia() == null) {
            throw new RuntimeException("Not nul");
        }
        return new IntegranteResp(integranteR.save(advertencia.getIntegrante(integranteR.findById(advertencia.getId()).get())));
    }

    @Override
    public Integer[] delete(Integer[] ids) {
        for (Integer id : ids) {
            integranteR.deleteById(id);
        }
        return ids;
    }

}
