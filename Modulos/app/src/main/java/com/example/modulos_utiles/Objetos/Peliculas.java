package com.example.modulos_utiles.Objetos;

public class Peliculas {

    private String titulo;
    private String descripcion;
    private float calificacion;
    private String portada;

    public Peliculas() {

    }

    public Peliculas(String titulo, String descripcion, float calificacion, String portada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.portada = portada;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public String getPortada() {
        return portada;
    }

}
