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
        // Validar que el CIF no exista
        if (profesorRepository.findProfesorByCif(profesor.getCif()).isPresent()) {
            throw new IllegalArgumentException("El CIF ya existe: " + profesor.getCif());
        }

        // Validar la facultad
        if (profesor.getFacultad() != null && profesor.getFacultad().getNombre() != null) {
            var facultad = facultadRepository.findFacultadByNombre(profesor.getFacultad().getNombre());
            if (facultad == null) {
                throw new IllegalArgumentException("Facultad no encontrada: " + profesor.getFacultad().getNombre());
            }
            profesor.setFacultad(facultad);
        }

        // Guardar el profesor
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
