package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Alumno;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    // AlumnoRepository.java
    Optional<Alumno> findAlumnoByCif(String cif);

}
