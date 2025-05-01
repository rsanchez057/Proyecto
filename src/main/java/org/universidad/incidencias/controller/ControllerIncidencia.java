package org.universidad.incidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.IncidenciaDTO;
import org.universidad.incidencias.model.Incidencia;
import org.universidad.incidencias.service.IncidenciaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/incidencia")
public class ControllerIncidencia {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping("/all")
    public List<IncidenciaDTO> getAll() {
        return incidenciaService.getAll().stream()
                .map(incidencia -> {
                    IncidenciaDTO dto = new IncidenciaDTO();
                    dto.setTitulo(incidencia.getTitulo());
                    dto.setDescripcion(incidencia.getDescripcion());
                    dto.setFecha(incidencia.getFecha().toString());
                    dto.setEstado(incidencia.getEstado());
                    dto.setTipo(incidencia.getTipo());
                    dto.setCifAlumno(incidencia.getAlumno().getCif());
                    dto.setCifProfesor(incidencia.getProfesor().getCif());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void save(@RequestBody IncidenciaDTO incidenciaDTO) {
        incidenciaService.save(dtoToIncidencia(incidenciaDTO));
    }

    @PutMapping("/update")
    public void update(@RequestBody IncidenciaDTO incidenciaDTO) {
        incidenciaService.update(dtoToIncidencia(incidenciaDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        incidenciaService.delete(id);
    }

    private Incidencia dtoToIncidencia(IncidenciaDTO dto) {
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo(dto.getTitulo());
        incidencia.setDescripcion(dto.getDescripcion());
        incidencia.setFecha(java.time.LocalDate.parse(dto.getFecha()));
        incidencia.setEstado(dto.getEstado());
        incidencia.setTipo(dto.getTipo());

        // Aquí solo se setean los objetos con su cif para que el Service los resuelva
        incidencia.setAlumno(new org.universidad.incidencias.model.Alumno());
        incidencia.getAlumno().setCif(dto.getCifAlumno());

        incidencia.setProfesor(new org.universidad.incidencias.model.Profesor());
        incidencia.getProfesor().setCif(dto.getCifProfesor());

        return incidencia;
    }
}
