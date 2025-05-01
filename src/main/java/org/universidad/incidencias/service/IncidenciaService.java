package org.universidad.incidencias.service;

import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Incidencia;

import java.util.List;

@Service
public interface IncidenciaService {

    List<Incidencia> getAll();
    Incidencia getOne(Integer id);
    Incidencia save(Incidencia incidencia);
    Incidencia update(Incidencia incidencia);
    void delete(Integer id);


}
