package org.universidad.incidencias.dto;

public class IncidenciaDTO {

    private String titulo;
    private String descripcion;
    private String fecha;
    private String estado;
    private String tipo;
    private String cifAlumno;
    private String cifProfesor;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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
}
