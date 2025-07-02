package org.universidad.incidencias.service;

import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Incidencia;

import java.util.List;
import org.universidad.incidencias.dto.IncidenciaDTO;
@Service
public interface IncidenciaService {

    List<Incidencia> getAll();
    Incidencia getOne(Integer id);
    void save(Incidencia incidencia, String cifUsuario, String rolUsuario);
    Incidencia update(Incidencia incidencia);
    void delete(Integer id);
    List<IncidenciaDTO> obtenerIncidenciasDeFacultad(String cifCoordinador);
}
