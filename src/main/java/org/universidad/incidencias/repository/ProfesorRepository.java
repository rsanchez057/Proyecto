package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Profesor;

import java.util.List;
import java.util.Optional;
import org.universidad.incidencias.model.Facultad;
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String> {

    // AlumnoRepository.java
    Optional<Profesor> findProfesorByCif(String cif);
    boolean existsByCif(String cif);
    List<Profesor> findByFacultad(Facultad facultad);
}
