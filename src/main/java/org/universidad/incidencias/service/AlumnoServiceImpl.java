package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.repository.AlumnoRepository;
import org.universidad.incidencias.repository.FacultadRepository;

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

    @Autowired
    private FacultadRepository facultadRepository;

    @Override
    public Alumno save(Alumno alumno) {
        //comprabar cif
        if (alumnoRepository.findAlumnoByCif(alumno.getCif()).isPresent()) {
            throw new RuntimeException("El cif ya existe");
        }

        //comprobar facultad
        if (alumno.getFacultad() != null && alumno.getFacultad().getNombre() != null) {
            Facultad facultad = facultadRepository.findFacultadByNombre(alumno.getFacultad().getNombre());
            if (facultad == null) {
                throw new IllegalArgumentException("Facultad con nombre '" + alumno.getFacultad().getNombre() + "' no existe");
            }
            alumno.setFacultad(facultad);
        }
        return alumnoRepository.save(alumno);
    }

    @Override
    public Alumno update(Alumno alumno) {
        Alumno alumnoDB = alumnoRepository.findAlumnoByCif(alumno.getCif())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setEmail(alumno.getEmail());

        if (alumno.getFacultad() != null && alumno.getFacultad().getNombre() != null) {
            Facultad facultad = facultadRepository.findFacultadByNombre(alumno.getFacultad().getNombre());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + alumno.getFacultad().getNombre() + "' no existe");
            }
            alumnoDB.setFacultad(facultad);
        }

        return alumnoRepository.save(alumnoDB);
    }



    @Override
    public void delete(String cif) {
        alumnoRepository.delete(getOne(cif));
    }
}
