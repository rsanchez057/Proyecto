package org.universidad.incidencias.security;

import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.repository.ProfesorRepository;
import org.universidad.incidencias.repository.CoordinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profesor> profesor = profesorRepository.findProfesorByCif(username);
        if (profesor.isPresent()) {
            return new User(
                    profesor.get().getCif(),
                    profesor.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_PROFESOR"))
            );
        }

        Optional<Coordinador> coordinador = coordinadorRepository.findCoordinadorByCif(username);
        if (coordinador.isPresent()) {
            return new User(
                    coordinador.get().getCif(),
                    coordinador.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_COORDINADOR"))
            );
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}