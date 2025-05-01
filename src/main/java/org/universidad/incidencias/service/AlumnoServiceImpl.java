package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.repository.AlumnoRepository;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Alumno> getAll() {
        return alumnoRepository.findAll();
    }

    @Override
    public Alumno getOne(String cif) {
        return alumnoRepository.findAlumnoByCif(cif).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
    }

    @Override
    public Alumno save(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    public Alumno update(Alumno alumno) {
        Alumno alumnoDB = alumnoRepository.findAlumnoByCif(alumno.getCif())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setEmail(alumno.getEmail());
        return alumnoRepository.save(alumnoDB);
    }


    @Override
    public void delete(String cif) {
        alumnoRepository.delete(getOne(cif));
    }
}
