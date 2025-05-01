package org.universidad.incidencias.service;



import org.springframework.stereotype.Service;
import org.universidad.incidencias.model.Facultad;
import java.util.List;

@Service
public interface FacultadService {

    List<Facultad> getAll();

    Facultad getOne(Integer id);

    Facultad save(Facultad facultad);

    Facultad update(Facultad facultad);

    void delete(Integer id);
}