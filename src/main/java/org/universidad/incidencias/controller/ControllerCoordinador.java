package org.universidad.incidencias.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.CoordinadorDTO;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.service.CoordinadorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coordinador")
public class ControllerCoordinador {

    @Autowired
    private CoordinadorService coordinadorService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<CoordinadorDTO> getAll() {
        return coordinadorService.getAll().stream()
                .map(coordinador -> modelMapper.map(coordinador, CoordinadorDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void save(@RequestBody CoordinadorDTO coordinadorDTO) {
        Coordinador coordinador = modelMapper.map(coordinadorDTO, Coordinador.class);
        modelMapper.map(coordinadorService.save(coordinador), CoordinadorDTO.class);
    }

    @PutMapping("/update")
    public void update(@RequestBody CoordinadorDTO coordinadorDTO) {
        coordinadorService.update(modelMapper.map(coordinadorDTO, Coordinador.class));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        coordinadorService.delete(id);
    }
}
