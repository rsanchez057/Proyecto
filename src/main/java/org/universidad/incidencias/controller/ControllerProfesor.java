package org.universidad.incidencias.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.ProfesorDTO;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.service.ProfesorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profesor")
public class ControllerProfesor {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<ProfesorDTO> getAll() {
        return profesorService.getAll().stream()
                .map(profesor -> modelMapper.map(profesor, ProfesorDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void save(@RequestBody ProfesorDTO profesorDTO) {
        Profesor profesor = modelMapper.map(profesorDTO, Profesor.class);
        modelMapper.map(profesorService.save(profesor), ProfesorDTO.class);
    }

    @PutMapping("/update")
    public void update(@RequestBody ProfesorDTO profesorDTO) {
        profesorService.update(modelMapper.map(profesorDTO, Profesor.class));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        profesorService.delete(id);
    }
}
