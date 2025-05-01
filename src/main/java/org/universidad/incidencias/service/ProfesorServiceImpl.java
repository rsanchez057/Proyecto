package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.repository.FacultadRepository;
import org.universidad.incidencias.repository.ProfesorRepository;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Profesor> getAll() {
        return profesorRepository.findAll();
    }

    @Override
    public Profesor getOne(String cif) {
        return profesorRepository.findProfesorByCif(cif).orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
    }

    @Autowired
    private FacultadRepository facultadRepository;

    @Override
    public Profesor save(Profesor profesor) {
        // comprobar cif
        if (profesorRepository.findProfesorByCif(profesor.getCif()).isPresent()) {
            throw new RuntimeException("El cif ya existe");
        }

        // comprobar facultad
        if (profesor.getFacultad() != null && profesor.getFacultad().getNombre() != null) {
            var facultad = facultadRepository.findFacultadByNombre(profesor.getFacultad().getNombre());
            if (facultad == null) {
                throw new IllegalArgumentException("Facultad con nombre '" + profesor.getFacultad().getNombre() + "' no existe");
            }
            profesor.setFacultad(facultad);
        }

        return profesorRepository.save(profesor);
    }

    @Override
    public Profesor update(Profesor profesor) {
        Profesor profesorDB = profesorRepository.findProfesorByCif(profesor.getCif())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        profesorDB.setNombre(profesor.getNombre());
        profesorDB.setEmail(profesor.getEmail());

        if (profesor.getFacultad() != null && profesor.getFacultad().getNombre() != null) {
            var facultad = facultadRepository.findFacultadByNombre(profesor.getFacultad().getNombre());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + profesor.getFacultad().getNombre() + "' no existe");
            }
            profesorDB.setFacultad(facultad);
        }

        return profesorRepository.save(profesorDB);
    }

    @Override
    public void delete(String cif) {
        profesorRepository.delete(getOne(cif));
    }
}
