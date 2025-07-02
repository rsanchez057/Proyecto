package org.universidad.incidencias.dto;

import java.time.LocalDate;

public class IncidenciaDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha; // Corregido: ahora es LocalDate
    private String estado;
    private String tipo;
    private String cifAlumno;
    private String cifProfesor;
    private String nombreAlumno;
    private String nombreProfesor;

    // Constructor sin argumentos
    public IncidenciaDTO() {
    }

    // Constructor con todos los parámetros
    public IncidenciaDTO(Integer id, String titulo, String descripcion, LocalDate fecha, String estado, String tipo,
                         String cifAlumno, String cifProfesor, String nombreAlumno, String nombreProfesor) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.tipo = tipo;
        this.cifAlumno = cifAlumno;
        this.cifProfesor = cifProfesor;
        this.nombreAlumno = nombreAlumno;
        this.nombreProfesor = nombreProfesor;
    }

// Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCifAlumno() {
        return cifAlumno;
    }

    public void setCifAlumno(String cifAlumno) {
        this.cifAlumno = cifAlumno;
    }

    public String getCifProfesor() {
        return cifProfesor;
    }

    public void setCifProfesor(String cifProfesor) {
        this.cifProfesor = cifProfesor;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }
}