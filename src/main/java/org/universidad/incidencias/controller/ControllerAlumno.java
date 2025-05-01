package org.universidad.incidencias.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.AlumnoDTO;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.service.AlumnoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumno")
public class ControllerAlumno {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private org.universidad.incidencias.repository.FacultadRepository facultadRepository;


    @GetMapping("/all")
    public List<AlumnoDTO> getAll() {
        return alumnoService.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{cif}")
    public AlumnoDTO getOne(@PathVariable String cif) {
        return convertToDTO(alumnoService.getOne(cif));
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        AlumnoDTO dto = modelMapper.map(alumno, AlumnoDTO.class);
        if (alumno.getFacultad() != null) {
            dto.setNombreFacultad(alumno.getFacultad().getNombre());
        }
        return dto;
    }

    @PostMapping("/save")
    public void save(@RequestBody AlumnoDTO alumnoDTO) {
        Alumno alumno = modelMapper.map(alumnoDTO, Alumno.class);

        if (alumnoDTO.getNombreFacultad() != null) {
            Facultad facultad = facultadRepository.findFacultadByNombre(alumnoDTO.getNombreFacultad());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + alumnoDTO.getNombreFacultad() + "' no existe");
            }
            alumno.setFacultad(facultad);
        }

        alumnoService.save(alumno);
    }


    @PutMapping("/update")
    public void update(@RequestBody AlumnoDTO alumnoDTO) {
        alumnoService.update(modelMapper.map(alumnoDTO, Alumno.class));
    }

    @DeleteMapping("/delete/{cif}")
    public void delete(@PathVariable String cif) {
        alumnoService.delete(cif);
    }
}

