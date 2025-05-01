package org.universidad.incidencias.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.FacultadDTO;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.service.FacultadService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/facultad")
public class ControllerFacultad {

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<FacultadDTO> getAll() {
        return facultadService.getAll().stream().map(
                facultad -> modelMapper.map(facultad, FacultadDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void save(@RequestBody FacultadDTO facultadDTO) {
        Facultad facultadDB = modelMapper.map(facultadDTO, Facultad.class);
        modelMapper.map(facultadService.save(facultadDB), FacultadDTO.class);
    }

    @PutMapping("/update")
    public void update(@RequestBody FacultadDTO facultadDTO) {
        facultadService.update(modelMapper.map(facultadDTO, Facultad.class));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        facultadService.delete(id);
    }

}

