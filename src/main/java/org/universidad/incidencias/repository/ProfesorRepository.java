package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Profesor;

import java.util.List;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

    // AlumnoRepository.java
    Profesor findProfesorBy(String cif);

}
