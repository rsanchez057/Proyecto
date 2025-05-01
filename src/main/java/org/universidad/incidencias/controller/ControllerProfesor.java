package org.universidad.incidencias.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.ProfesorDTO;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.service.ProfesorService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/profesor")
public class ControllerProfesor {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private org.universidad.incidencias.repository.FacultadRepository facultadRepository;


    @GetMapping("/all")
    public List<ProfesorDTO> getAll() {
        return profesorService.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{cif}")
    public ProfesorDTO getOne(@PathVariable String cif) {
        return convertToDTO(profesorService.getOne(cif));
    }

    @PostMapping("/save")
    public void save(@RequestBody ProfesorDTO profesorDTO) {
        Profesor profesor = modelMapper.map(profesorDTO, Profesor.class);

        if (profesorDTO.getNombreFacultad() != null) {
            var facultad = facultadRepository.findFacultadByNombre(profesorDTO.getNombreFacultad());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + profesorDTO.getNombreFacultad() + "' no existe");
            }
            profesor.setFacultad(facultad);
        }

        profesorService.save(profesor);
    }

    private ProfesorDTO convertToDTO(Profesor profesor) {
        ProfesorDTO dto = modelMapper.map(profesor, ProfesorDTO.class);
        if (profesor.getFacultad() != null) {
            dto.setNombreFacultad(profesor.getFacultad().getNombre());
        }
        return dto;
    }


    @PutMapping("/update")
    public void update(@RequestBody ProfesorDTO profesorDTO) {
        Profesor profesor = modelMapper.map(profesorDTO, Profesor.class);

        if (profesorDTO.getNombreFacultad() != null) {
            var facultad = facultadRepository.findFacultadByNombre(profesorDTO.getNombreFacultad());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + profesorDTO.getNombreFacultad() + "' no existe");
            }
            profesor.setFacultad(facultad);
        }

        profesorService.update(profesor);
    }


    @DeleteMapping("/delete/{cif}")
    public void delete(@PathVariable String cif) {
        profesorService.delete(cif);
    }
}
