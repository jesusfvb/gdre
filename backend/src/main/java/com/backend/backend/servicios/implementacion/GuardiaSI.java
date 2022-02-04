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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuardiaSI implements GuardiaS {

    @Autowired
    private GuardiaR guardiaR;

    @Autowired
    private UsuarioS usuarioS;

    @Override
    public List<GuardiaResp> listar() {
        List<GuardiaResp> salida = new LinkedList<>();
        guardiaR.findAll().forEach(guardia -> salida.add(guardia.convertir()));
        return salida;
    }

    @Override
    public List<GuardiaResp> listarResidencia() {
        return guardiaR.findAllByUbicacionIsResidencia()
                .parallelStream().map(Guardia::convertir)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuardiaResp> listarDocente() {
        return guardiaR.findAllByUbicacionIsDocente()
                .parallelStream().map(Guardia::convertir)
                .collect(Collectors.toList());
    }

    @Override
    public GuardiaResp salvarResidencia(GuardiaResidenciaSol guardia) {
        return guardiaR.save(new Guardia(guardia)).convertir();
    }

    @Override
    public GuardiaResp salvarDocente(GuardiaDocenteSol guardia) {
        Guardia salida = guardiaR.save(new Guardia(guardia));
        salida.setCoordinador(usuarioS.getPorId(guardia.getIdCoordinador()));
        return salida.convertir();
    }

    @Override
    public GuardiaResp modificarResidencia(GuardiaResidenciaUpSol guardia) {
        Guardia salida = guardiaR.getById(guardia.getId());
        salida.setFecha(guardia.getFecha());
        guardiaR.save(salida);
        return salida.convertir();
    }

    @Override
    public GuardiaResp modificarDocente(GuardiaDocenteUpSol guardia) {
        Guardia salida = guardiaR.getById(guardia.getId());
        salida.setCoordinador(usuarioS.getPorId(guardia.getIdCoordinador()));
        salida.setFecha(guardia.getFecha());
        guardiaR.save(salida);
        return salida.convertir();
    }

    @Override
    public Integer[] borrar(Integer[] ids) {
        Arrays.stream(ids).forEach(id -> guardiaR.deleteById(id));
        return ids;
    }

}
