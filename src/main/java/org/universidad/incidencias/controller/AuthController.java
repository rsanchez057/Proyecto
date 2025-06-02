package org.universidad.incidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.universidad.incidencias.model.Usuario;
import org.universidad.incidencias.repository.UsuarioRepository;
import org.universidad.incidencias.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "Usuario registrado exitosamente";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Autenticación del usuario
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Si pasó la autenticación, generamos el token
        return jwtUtil.generateToken(username);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
