package org.universidad.incidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.dto.IncidenciaDTO;
import org.universidad.incidencias.model.Incidencia;
import org.universidad.incidencias.service.IncidenciaService;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/incidencia")
public class ControllerIncidencia {

    @Autowired
    private IncidenciaService incidenciaService;

    @PreAuthorize("hasAuthority('ROLE_PROFESOR') or hasAuthority('ROLE_COORDINADOR')")
    @GetMapping("/all")
    public ResponseEntity<List<IncidenciaDTO>> getAll() {
        List<IncidenciaDTO> incidencias = incidenciaService.getAll().stream()
                .map(incidencia -> {
                    IncidenciaDTO dto = new IncidenciaDTO();
                    dto.setTitulo(incidencia.getTitulo());
                    dto.setDescripcion(incidencia.getDescripcion());
                    dto.setFecha(incidencia.getFecha()); // Ya es LocalDate
                    dto.setEstado(incidencia.getEstado());
                    dto.setTipo(incidencia.getTipo());
                    dto.setCifAlumno(incidencia.getAlumno() != null ? incidencia.getAlumno().getCif() : null);
                    dto.setCifProfesor(incidencia.getProfesor() != null ? incidencia.getProfesor().getCif() : null);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(incidencias);
    }

    @PreAuthorize("hasAuthority('ROLE_COORDINADOR')")
    @GetMapping("/facultad")
    public List<IncidenciaDTO> getIncidenciasCoordinador(Authentication authentication) {
        String cif = authentication.getName(); // CIF del coordinador autenticado
        return incidenciaService.obtenerIncidenciasDeFacultad(cif);
    }

    @PreAuthorize("hasAuthority('ROLE_PROFESOR') or hasAuthority('ROLE_COORDINADOR')")
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody IncidenciaDTO incidenciaDTO, Authentication auth) {
        try {
            String cifUsuario = auth.getName(); // El username es el CIF
            String rolUsuario = auth.getAuthorities().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"))
                    .getAuthority();

            incidenciaService.save(dtoToIncidencia(incidenciaDTO), cifUsuario, rolUsuario);
            return ResponseEntity.status(201).body("Incidencia creada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody IncidenciaDTO incidenciaDTO) {
        try {
            incidenciaService.update(dtoToIncidencia(incidenciaDTO));
            return ResponseEntity.ok("Incidencia actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            incidenciaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/facultad/{cifCoordinador}")
    public ResponseEntity<List<IncidenciaDTO>> obtenerIncidenciasDeFacultad(@PathVariable String cifCoordinador) {
        try {
            List<IncidenciaDTO> incidencias = incidenciaService.obtenerIncidenciasDeFacultad(cifCoordinador);
            return ResponseEntity.ok(incidencias);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private Incidencia dtoToIncidencia(IncidenciaDTO dto) {
        if (dto.getFecha() == null || dto.getTitulo() == null || dto.getDescripcion() == null) {
            throw new RuntimeException("Campos obligatorios faltantes");
        }

        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo(dto.getTitulo());
        incidencia.setDescripcion(dto.getDescripcion());
        incidencia.setFecha(dto.getFecha()); // No hace falta convertir, ya es LocalDate
        incidencia.setEstado(dto.getEstado());
        incidencia.setTipo(dto.getTipo());

        // Creación de alumno y profesor con los datos de la incidencia
        incidencia.setAlumno(new org.universidad.incidencias.model.Alumno());
        incidencia.getAlumno().setCif(dto.getCifAlumno());

        incidencia.setProfesor(new org.universidad.incidencias.model.Profesor());
        incidencia.getProfesor().setCif(dto.getCifProfesor());

        return incidencia;
    }
}
