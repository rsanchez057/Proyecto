package org.universidad.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.universidad.incidencias.model.Coordinador;

import java.util.List;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, Integer> {

   Coordinador findCoordinadorByCif(String cif);
}
