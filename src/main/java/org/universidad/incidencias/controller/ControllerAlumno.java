package org.universidad.incidencias.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.AlumnoDTO;
import org.universidad.incidencias.model.Alumno;
import org.universidad.incidencias.service.AlumnoService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/alumno")
public class ControllerAlumno {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<AlumnoDTO> getAll() {
        return alumnoService.getAll().stream()
                .map(alumno -> modelMapper.map(alumno, AlumnoDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/get/{cif}")
    public AlumnoDTO getOne(@PathVariable String cif) {
        return modelMapper.map(alumnoService.getOne(cif), AlumnoDTO.class);
    }

    @PostMapping("/save")
    public void save(@RequestBody AlumnoDTO alumnoDTO) {
        Alumno alumno = modelMapper.map(alumnoDTO, Alumno.class);
        modelMapper.map(alumnoService.save(alumno), AlumnoDTO.class);
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

