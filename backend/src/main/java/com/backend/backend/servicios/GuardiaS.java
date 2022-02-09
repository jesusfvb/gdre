package com.backend.backend.servicios;

import com.backend.backend.controlador.respuestas.GuardiaResp;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaDocenteUpSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaSol;
import com.backend.backend.controlador.solicitudes.guardian.GuardiaResidenciaUpSol;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuardiaS {

    List<GuardiaResp> listar();

    List<GuardiaResp> listarResidencia();

    List<GuardiaResp> listarResidencia(Integer id);

    List<GuardiaResp> listarDocente();

    List<GuardiaResp> listarDocente(Integer id);

    GuardiaResp salvarResidencia(GuardiaResidenciaSol guardia);

    GuardiaResp salvarDocente(GuardiaDocenteSol guardia);

    GuardiaResp modificarResidencia(GuardiaResidenciaUpSol guardia);

    GuardiaResp modificarDocente(GuardiaDocenteUpSol guardia);

    Integer[] borrar(Integer[] ids);
}
