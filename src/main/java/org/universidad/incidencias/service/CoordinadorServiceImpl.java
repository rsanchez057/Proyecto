package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Coordinador;
import org.universidad.incidencias.repository.CoordinadorRepository;

import java.util.List;

@Service
public class CoordinadorServiceImpl implements CoordinadorService {

    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Coordinador> getAll() {
        return coordinadorRepository.findAll();
    }

    @Override
    public Coordinador getOne(String cif) {
        return coordinadorRepository.findCoordinadorByCif(cif);
    }

    @Override
    public Coordinador save(Coordinador coordinador) {
        return coordinadorRepository.save(coordinador);
    }

    @Override
    public Coordinador update(Coordinador coordinador) {
        Coordinador coordinadorDB = coordinadorRepository.findCoordinadorByCif(coordinador.getCif());
        coordinadorDB.setNombre(coordinador.getNombre());
        coordinadorDB.setEmail(coordinador.getEmail());
        return coordinadorRepository.save(coordinadorDB);
    }

    @Override
    public void delete(String cif) {
        coordinadorRepository.delete(getOne(cif));
    }
}
