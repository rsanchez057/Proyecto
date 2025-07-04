package org.universidad.incidencias.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.CoordinadorDTO;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.service.CoordinadorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.universidad.incidencias.service.IncidenciaService;
import org.universidad.incidencias.dto.IncidenciaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.stream.Collectors;
import org.universidad.incidencias.model.Incidencia;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/coordinador")
@PreAuthorize("hasRole('COORDINADOR')")
public class ControllerCoordinador {

    @Autowired
    private CoordinadorService coordinadorService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private org.universidad.incidencias.repository.FacultadRepository facultadRepository;

    @GetMapping("/all")
    public List<CoordinadorDTO> getAll() {
        return coordinadorService.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CoordinadorDTO convertToDTO(Coordinador coordinador) {
        CoordinadorDTO dto = modelMapper.map(coordinador, CoordinadorDTO.class);
        if (coordinador.getFacultad() != null) {
            dto.setNombreFacultad(coordinador.getFacultad().getNombre());
        }
        return dto;
    }

    @GetMapping("/get/{cif}")
    public CoordinadorDTO getOne(@PathVariable String cif) {
        return modelMapper.map(coordinadorService.getOne(cif), CoordinadorDTO.class);
    }

    @PostMapping("/save")
    public void save(@RequestBody CoordinadorDTO coordinadorDTO) {
        Coordinador coordinador = modelMapper.map(coordinadorDTO, Coordinador.class);

        if (coordinadorDTO.getNombreFacultad() != null) {
            Facultad facultad = facultadRepository.findFacultadByNombre(coordinadorDTO.getNombreFacultad());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + coordinadorDTO.getNombreFacultad() + "' no existe");
            }
            coordinador.setFacultad(facultad);
        }

        coordinadorService.save(coordinador);
    }


    @PutMapping("/update")
    public void update(@RequestBody CoordinadorDTO coordinadorDTO) {
        Coordinador coordinador = modelMapper.map(coordinadorDTO, Coordinador.class);

        if (coordinadorDTO.getNombreFacultad() != null) {
            Facultad facultad = facultadRepository.findFacultadByNombre(coordinadorDTO.getNombreFacultad());
            if (facultad == null) {
                throw new RuntimeException("Facultad con nombre '" + coordinadorDTO.getNombreFacultad() + "' no existe");
            }
            coordinador.setFacultad(facultad);
        }

        coordinadorService.update(coordinador);
    }


    @DeleteMapping("/delete/{cif}")
    public void delete(@PathVariable String cif) {
        coordinadorService.delete(cif);
    }

    @Autowired
    private IncidenciaService incidenciaService;

    @PostMapping("/incidencia")
    public ResponseEntity<?> crearIncidencia(@RequestBody IncidenciaDTO dto, Authentication auth) {
        String cif = auth.getName();
        String rol = auth.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"))
                .getAuthority();
        Incidencia incidencia = modelMapper.map(dto, Incidencia.class);
        incidenciaService.save(incidencia, cif, rol);
        return ResponseEntity.ok("Incidencia registrada");
    }

    @GetMapping("/incidencias")
    public ResponseEntity<?> obtenerIncidenciasFacultad(Authentication auth) {
        String cif = auth.getName();
        List<IncidenciaDTO> incidencias = incidenciaService.obtenerIncidenciasDeFacultad(cif);
        return ResponseEntity.ok(incidencias);
    }
}
