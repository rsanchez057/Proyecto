package org.universidad.incidencias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.dto.IncidenciaDTO;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.model.Incidencia;
import org.universidad.incidencias.repository.AlumnoRepository;
import org.universidad.incidencias.repository.CoordinadorRepository;
import org.universidad.incidencias.repository.ProfesorRepository;
import org.universidad.incidencias.repository.IncidenciaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Override
    public List<Incidencia> getAll() {
        return incidenciaRepository.findAll();
    }

    public List<IncidenciaDTO> obtenerIncidenciasDeFacultad(String cifCoordinador) {
        Coordinador coord = coordinadorRepository.findCoordinadorByCif(cifCoordinador)
                .orElseThrow(() -> new RuntimeException("Coordinador con CIF " + cifCoordinador + " no existe"));
        List<Profesor> profesores = profesorRepository.findByFacultad(coord.getFacultad());
        return incidenciaRepository.findByProfesorIn(profesores)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Incidencia getOne(Integer id) {
        return incidenciaRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Incidencia incidencia, String cifUsuario, String rolUsuario) {
// Validar alumno
        String alumnoCif = incidencia.getAlumno().getCif();
        Alumno alumno = alumnoRepository.findAlumnoByCif(alumnoCif)
                .orElseThrow(() -> new RuntimeException("Alumno con CIF " + alumnoCif + " no existe"));
        Profesor profesor;

        if ("ROLE_PROFESOR".equalsIgnoreCase(rolUsuario)) {
            // El profesor que reporta debe existir
            profesor = profesorRepository.findProfesorByCif(cifUsuario)
                    .orElseThrow(() -> new RuntimeException("Profesor con CIF " + cifUsuario + " no existe"));
        } else if ("ROLE_COORDINADOR".equalsIgnoreCase(rolUsuario)) {
            // El coordinador debe haber proporcionado un profesor válido en la incidencia
            String profesorCif = incidencia.getProfesor().getCif();
            profesor = profesorRepository.findProfesorByCif(profesorCif)
                    .orElseThrow(() -> new RuntimeException("Profesor con CIF " + profesorCif + " no existe"));
        } else {
            throw new RuntimeException("Rol de usuario no válido: " + rolUsuario);
        }

       // Asignar alumno y profesor validados
        incidencia.setAlumno(alumno);
        incidencia.setProfesor(profesor);

        // Guardar la incidencia
        incidenciaRepository.save(incidencia);
    }

        @Override
    public Incidencia update(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    public void delete(Integer id) {
        incidenciaRepository.deleteById(id);
    }

    private IncidenciaDTO toDTO(Incidencia incidencia) {
        return new IncidenciaDTO(
                incidencia.getId(),
                incidencia.getTitulo(), // Asegúrate de que el campo `titulo` exista en la entidad
                incidencia.getDescripcion(),
                incidencia.getFecha(), // Asegúrate de que el campo `fecha` exista en la entidad
                incidencia.getEstado(), // Asegúrate de que el campo `estado` exista en la entidad
                incidencia.getTipo(), // Asegúrate de que el campo `tipo` exista en la entidad
                incidencia.getAlumno() != null ? incidencia.getAlumno().getCif() : null,
                incidencia.getProfesor() != null ? incidencia.getProfesor().getCif() : null,
                incidencia.getAlumno() != null ? incidencia.getAlumno().getNombre() : null,
                incidencia.getProfesor() != null ? incidencia.getProfesor().getNombre() : null
        );
    }
}