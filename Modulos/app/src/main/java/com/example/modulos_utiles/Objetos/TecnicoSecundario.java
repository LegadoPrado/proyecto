package com.example.modulos_utiles.Objetos;

public class TecnicoSecundario {

    private int clv_Tecnico;
    private String nombre;

    public TecnicoSecundario(int clv_Tecnico, String nombre) {
        this.clv_Tecnico = clv_Tecnico;
        this.nombre = nombre;
    }

    public int getClv_Tecnico() {
        return clv_Tecnico;
    }

    public String getNombre() {
        return nombre;
    }
}
