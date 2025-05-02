package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.repository.CoordinadorRepository;
import org.universidad.incidencias.repository.FacultadRepository;

import java.util.List;

@Service
public class CoordinadorServiceImpl implements CoordinadorService {

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FacultadRepository facultadRepository;

    @Override
    public List<Coordinador> getAll() {
        return coordinadorRepository.findAll();
    }

    @Override
    public Coordinador getOne(String cif) {
        return coordinadorRepository.findCoordinadorByCif(cif);
    }

    @Override
    public Coordinador save(Coordinador coordinador) {

        // Comprobar cif
        if (coordinadorRepository.findCoordinadorByCif(coordinador.getCif()) != null) {
            throw new RuntimeException("El cif ya existe");
        }
         // Comprobar facultad
        if (coordinador.getFacultad() != null && coordinador.getFacultad().getNombre() != null) {
            var facultad = facultadRepository.findFacultadByNombre(coordinador.getFacultad().getNombre());
            if (facultad == null) {
                throw new IllegalArgumentException("Facultad con nombre '" + coordinador.getFacultad().getNombre() + "' no existe");
            }
            coordinador.setFacultad(facultad);
        }
        return coordinadorRepository.save(coordinador);
    }

    @Override
    public Coordinador update(Coordinador coordinador) {
        Coordinador coordinadorDB = coordinadorRepository.findCoordinadorByCif(coordinador.getCif());
        if (coordinadorDB == null) {
            throw new RuntimeException("Coordinador no encontrado");
        }
        coordinadorDB.setNombre(coordinador.getNombre());
        coordinadorDB.setEmail(coordinador.getEmail());

        if (coordinador.getFacultad() != null && coordinador.getFacultad().getNombre() != null) {
            var facultad = facultadRepository.findFacultadByNombre(coordinador.getFacultad().getNombre());
            if (facultad == null) {
                throw new IllegalArgumentException("Facultad con nombre '" + coordinador.getFacultad().getNombre() + "' no existe");
            }
            coordinadorDB.setFacultad(facultad);
        }

        return coordinadorRepository.save(coordinadorDB);
    }

    @Override
    public void delete(String cif) {
        coordinadorRepository.delete(getOne(cif));
    }
}
