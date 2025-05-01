package org.universidad.incidencias.service;

import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.model.Profesor;

import java.util.List;

@Service
public interface CoordinadorService {

    List<Coordinador> getAll();
    Coordinador getOne(String cif);
    Coordinador save(Coordinador coordinador);
    Coordinador update(Coordinador coordinador);
    void delete(String cif);

}
