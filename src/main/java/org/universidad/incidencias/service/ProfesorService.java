package org.universidad.incidencias.service;

import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Profesor;

import java.util.List;

@Service
public interface ProfesorService {
    List<Profesor> getAll();

    Profesor getOne(Integer id);

    Profesor save(Profesor profesor);

    Profesor update(Profesor profesor);

    void delete(Integer id);

}
