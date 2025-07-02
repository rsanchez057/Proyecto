package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Coordinador;

import java.util.Optional;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, String> {
   Optional<Coordinador> findCoordinadorByCif(String cif);
   boolean existsCoordinadorByCif(String cif);
}
