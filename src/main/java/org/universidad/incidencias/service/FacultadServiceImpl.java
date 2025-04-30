package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.universidad.incidencias.model.Facultad;
import org.universidad.incidencias.repository.FacultadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultadServiceImpl implements FacultadService {

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private ModelMapper modelMapper;

    public FacultadServiceImpl(FacultadRepository facultadRepository) {
        this.facultadRepository = facultadRepository;
    }

    @Override
    public List<Facultad> getAll() {
        return facultadRepository.findAll();
    }

    @Override
    public Facultad getOne(Integer id) {
        return facultadRepository.findById(id).orElse(null);

    }

    @Override
    public Facultad save(Facultad facultad) {
        return facultadRepository.save(facultad);
    }

    @Override
    public Facultad update(Facultad facultad) {
        Facultad facultadDB = facultadRepository.findFacultadBy(facultad.getNombre());
        facultadDB.setDescripcion(facultad.getDescripcion());
        facultadDB.setNombre(facultad.getNombre());
        return facultadRepository.save(facultadDB);
    }

    @Override
    public void delete(Integer id) {
        facultadRepository.delete(getOne(id));
    }
}
