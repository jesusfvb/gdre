package com.backend.backend.servicios.implementacion;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteUpSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaUpSol;
import com.backend.backend.repositorio.GuardiaR;
import com.backend.backend.repositorio.entidad.Guardia;
import com.backend.backend.servicios.GuardiaS;
import com.backend.backend.servicios.UsuarioS;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GuardiaSI implements GuardiaS {

    private final GuardiaR guardiaR;

    private final UsuarioS usuarioS;

    @Override
    public List<GuardiaResp> listar() {
        return guardiaR.findAll().parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarResidencia() {
        return guardiaR.findAllByUbicacionIsResidencia().parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarDocente() {
        return guardiaR.findAllByUbicacionIsDocente().parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public GuardiaResp salvarResidencia(GuardiaResidenciaSol guardia) {
        return new GuardiaResp(guardiaR.save(guardia.getGuardia()));
    }

    @Override
    public GuardiaResp salvarDocente(GuardiaDocenteSol guardia) {
        return new GuardiaResp(guardiaR.save(guardia.getGuardia(usuarioS.getPorId(guardia.getIdCoordinador()))));
    }

    @Override
    public GuardiaResp modificarResidencia(GuardiaResidenciaUpSol guardia) {
        return new GuardiaResp(guardiaR.save(guardia.getGuardia(guardiaR.getById(guardia.getId()))));
    }

    @Override
    public GuardiaResp modificarDocente(GuardiaDocenteUpSol guardia) {
        return new GuardiaResp(guardiaR.save(guardia.getGuardia(guardiaR.getById(guardia.getId()), usuarioS.getPorId(guardia.getIdCoordinador()))));
    }

    @Override
    public Integer[] borrar(Integer[] ids) {
        Arrays.stream(ids).forEach(guardiaR::deleteById);
        return ids;
    }

}
