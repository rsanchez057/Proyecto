package org.universidad.incidencias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.repository.ProfesorRepository;
import org.universidad.incidencias.repository.CoordinadorRepository;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Override
    public Object obtenerPerfilDelUsuario(String cif) {
        var profesor = profesorRepository.findProfesorByCif(cif);
        if (profesor.isPresent()) {
            return profesor.get();
        }

        var coordinador = coordinadorRepository.findCoordinadorByCif(cif);
        if (coordinador.isPresent()) {
            return coordinador.get();
        }

        throw new RuntimeException("Usuario no encontrado");
    }
}
