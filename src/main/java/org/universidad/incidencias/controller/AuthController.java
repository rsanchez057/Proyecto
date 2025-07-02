package org.universidad.incidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.repository.FacultadRepository;
import org.universidad.incidencias.repository.ProfesorRepository;
import org.universidad.incidencias.repository.CoordinadorRepository;
import org.universidad.incidencias.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.universidad.incidencias.dto.ProfesorDTO;
import org.springframework.http.ResponseEntity;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.service.ProfesorService;
import org.universidad.incidencias.dto.CoordinadorDTO;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FacultadRepository facultadRepository;

    @PostMapping("/register/profesor")
    public ResponseEntity<String> registerProfesor(@RequestBody ProfesorDTO profesorDTO) {
        try {
            Profesor profesor = new Profesor();
            profesor.setCif(profesorDTO.getCif());
            profesor.setPassword(passwordEncoder.encode(profesorDTO.getPassword())); // Encriptar el password
            profesor.setNombre(profesorDTO.getNombre());
            profesor.setApellido(profesorDTO.getApellidos());
            profesor.setEmail(profesorDTO.getEmail());

            // Asociar la facultad
            Facultad facultad = new Facultad();
            facultad.setNombre(profesorDTO.getNombreFacultad());
            profesor.setFacultad(facultad);

            profesorService.save(profesor);
            return ResponseEntity.ok("Profesor registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/register/coordinador")
    public ResponseEntity<String> registerCoordinador(@RequestBody CoordinadorDTO coordinadorDTO) {
        try {
            Coordinador coordinador = new Coordinador();
            coordinador.setCif(coordinadorDTO.getCif());
            coordinador.setPassword(passwordEncoder.encode(coordinadorDTO.getPassword())); // Encriptar el password
            coordinador.setNombre(coordinadorDTO.getNombre());
            coordinador.setApellido(coordinadorDTO.getApellidos());
            coordinador.setEmail(coordinadorDTO.getEmail());

            // Asociar la facultad
            if (coordinadorDTO.getNombreFacultad() != null) {
                Facultad facultad = facultadRepository.findFacultadByNombre(coordinadorDTO.getNombreFacultad());
                if (facultad == null) {
                    throw new RuntimeException("Facultad con nombre '" + coordinadorDTO.getNombreFacultad() + "' no existe");
                }
                coordinador.setFacultad(facultad);
            }

            coordinadorRepository.save(coordinador);
            return ResponseEntity.ok("Coordinador registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Obtener detalles del usuario y roles
        var userDetails = userDetailsService.loadUserByUsername(username);

        // Generar token con roles
        return jwtUtil.generateToken(username, userDetails.getAuthorities());
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        if (jwtUtil.validateToken(token)) {
            return "Token válido";
        } else {
            return "Token inválido";
        }
    }
}