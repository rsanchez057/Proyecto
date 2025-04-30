package org.universidad.incidencias.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Incidencia;
import org.universidad.incidencias.repository.IncidenciaRepository;

import java.util.List;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Incidencia> getAll() {
        return incidenciaRepository.findAll();
    }

    @Override
    public Incidencia getOne(Integer id) {
        return incidenciaRepository.findById(id).orElse(null);
    }

    @Override
    public Incidencia save(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    public Incidencia update(Incidencia incidencia) {
        Incidencia incidenciaDB = incidenciaRepository.findIncidenciaByTitulo(incidencia.getTitulo());
        incidenciaDB.setDescripcion(incidencia.getDescripcion());
        incidenciaDB.setFecha(incidencia.getFecha());
        return incidenciaRepository.save(incidenciaDB);
    }

    @Override
    public void delete(Integer id) {
        incidenciaRepository.delete(getOne(id));
    }
}
