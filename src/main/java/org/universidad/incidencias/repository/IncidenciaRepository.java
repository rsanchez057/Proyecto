package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Incidencia;

import java.util.List;
import org.universidad.incidencias.model.Profesor;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    List<Incidencia> findByProfesorFacultadId(Integer idFacultad);
    List<Incidencia> findByProfesorIn(List<Profesor> profesores);

}
