package org.universidad.incidencias.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.model.Incidencia;
import org.universidad.incidencias.repository.AlumnoRepository;
import org.universidad.incidencias.repository.ProfesorRepository;
import org.universidad.incidencias.repository.IncidenciaRepository;

import java.util.List;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public List<Incidencia> getAll() {
        return incidenciaRepository.findAll();
    }

    @Override
    public Incidencia getOne(Integer id) {
        return incidenciaRepository.findById(id).orElse(null);
    }

    @Override
    public Incidencia save(Incidencia incidencia) {
        // Validar alumno
        String alumnoCif = incidencia.getAlumno().getCif();
        Alumno alumno = alumnoRepository.findAlumnoByCif(alumnoCif)
                .orElseThrow(() -> new RuntimeException("Alumno con CIF " + alumnoCif + " no existe"));

        // Validar profesor
        String profesorCif = incidencia.getProfesor().getCif();
        Profesor profesor = profesorRepository.findProfesorByCif(profesorCif)
                .orElseThrow(() -> new RuntimeException("Profesor con CIF " + profesorCif + " no existe"));

        // Asociar entidades válidas
        incidencia.setAlumno(alumno);
        incidencia.setProfesor(profesor);

        return incidenciaRepository.save(incidencia);
    }


    @Override
    public Incidencia update(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    public void delete(Integer id) {
        incidenciaRepository.deleteById(id);
    }
}

