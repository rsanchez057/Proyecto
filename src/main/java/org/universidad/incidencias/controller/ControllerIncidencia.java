package org.universidad.incidencias.controller;


import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<IncidenciaDTO> getAll() {
        return incidenciaService.getAll().stream()
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void save(@RequestBody IncidenciaDTO incidenciaDTO) {
        Incidencia incidencia = modelMapper.map(incidenciaDTO, Incidencia.class);
        modelMapper.map(incidenciaService.save(incidencia), IncidenciaDTO.class);
    }

    @PutMapping("/update")
    public void update(@RequestBody IncidenciaDTO incidenciaDTO) {
        incidenciaService.update(modelMapper.map(incidenciaDTO, Incidencia.class));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        incidenciaService.delete(id);
    }
}
