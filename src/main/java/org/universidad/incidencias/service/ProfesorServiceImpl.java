package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Profesor;
import org.universidad.incidencias.repository.ProfesorRepository;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Profesor> getAll() {
        return profesorRepository.findAll();
    }

    @Override
    public Profesor getOne(Integer id) {
        return profesorRepository.findById(id).orElse(null);
    }

    @Override
    public Profesor save(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @Override
    public Profesor update(Profesor profesor) {
        Profesor profesorDB = profesorRepository.findProfesorBy(profesor.getCif());
        profesorDB.setNombre(profesor.getNombre());
        profesorDB.setEmail(profesor.getEmail());
        return profesorRepository.save(profesorDB);
    }

    @Override
    public void delete(Integer id) {
        profesorRepository.delete(getOne(id));
    }
}
