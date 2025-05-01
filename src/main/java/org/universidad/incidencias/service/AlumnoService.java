package org.universidad.incidencias.service;

import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Alumno;

import java.util.List;

@Service
public interface AlumnoService {
    List<Alumno> getAll();

    Alumno getOne(String cif);

    Alumno save(Alumno alumno);

    Alumno update(Alumno alumno);

    void delete(String cif);


}
