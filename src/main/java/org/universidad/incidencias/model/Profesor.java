package org.universidad.incidencias.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profesor", uniqueConstraints = @UniqueConstraint(columnNames = "cif"))
public class Profesor extends Persona {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;


    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @OneToMany(mappedBy = "profesor")
    private List<Incidencia> incidencias;

    @Transient
    private Rol rol = Rol.PROFESOR;// Asignamos el rol por defecto a PROFESOR

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public String getApellido() {
        return super.getApellido();
    }

    @Override
    public void setApellido(String apellido) {
        super.setApellido(apellido);
    }

    @Override
    public String getCif() {
        return super.getCif();
    }

    @Override
    public void setCif(String cif) {
        super.setCif(cif);
    }
}
