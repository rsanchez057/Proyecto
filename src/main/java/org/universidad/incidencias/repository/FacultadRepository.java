package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Facultad;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar facultades por nombre o cualquier otro criterio

    @Query("select e from Facultad e where e.nombre = :nombre")
    Facultad findFacultadBy(String nombre);
}
