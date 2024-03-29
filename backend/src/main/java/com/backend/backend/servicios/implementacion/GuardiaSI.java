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
        return guardiaR.findByUbicacion(Guardia.Ubicacion.Residencia).parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarResidencia(Integer id) {
        return guardiaR.findByUbicacionAndIntegrantes_Participante_Id(Guardia.Ubicacion.Residencia, id).parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarDocente() {
        return guardiaR.findByUbicacion(Guardia.Ubicacion.Docente).parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarDocente(Integer id) {
        return guardiaR.findByUbicacionAndIntegrantes_Participante_Id(Guardia.Ubicacion.Docente, id).parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarDocenteProfesor(Integer id) {
        return guardiaR.findByUbicacionAndCoordinador_Id(Guardia.Ubicacion.Docente, id).parallelStream().map(GuardiaResp::new).collect(Collectors.toList());
    }

    @Override
    public GuardiaResp salvarResidencia(GuardiaResidenciaSol guardia) {
        if (guardiaR.existsByFechaAndUbicacion(guardia.getFecha(), Guardia.Ubicacion.Residencia)) {
            throw new RuntimeException("Guardia ya existe");
        }
        return new GuardiaResp(guardiaR.save(guardia.getGuardia()));
    }

    @Override
    public GuardiaResp salvarDocente(GuardiaDocenteSol guardia) {
        if (guardiaR.existsByFechaAndUbicacionAndCoordinador_Id(guardia.getFecha(), Guardia.Ubicacion.Docente, guardia.getIdCoordinador())) {
            throw new RuntimeException("Guardia ya existe");
        }
        return new GuardiaResp(guardiaR.save(guardia.getGuardia(usuarioS.getPorId(guardia.getIdCoordinador()))));
    }

    @Override
    public GuardiaResp modificarResidencia(GuardiaResidenciaUpSol guardia) {
        return new GuardiaResp(guardiaR.save(guardia.getGuardia(guardiaR.findById(guardia.getId()).get())));
    }

    @Override
    public GuardiaResp modificarDocente(GuardiaDocenteUpSol guardia) {
        return new GuardiaResp(guardiaR.save(guardia.getGuardia(guardiaR.findById(guardia.getId()).get(), usuarioS.getPorId(guardia.getIdCoordinador()))));
    }

    @Override
    public Integer[] borrar(Integer[] ids) {
        Arrays.stream(ids).forEach(guardiaR::deleteById);
        return ids;
    }

}
