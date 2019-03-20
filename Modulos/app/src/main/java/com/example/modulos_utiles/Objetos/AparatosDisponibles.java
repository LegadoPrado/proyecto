package com.example.modulos_utiles.Objetos;

public class AparatosDisponibles{
    private String Clv_Aparato;
    private String Descripcion;

    public AparatosDisponibles(String clv_Aparato, String descripcion) {
        Clv_Aparato = clv_Aparato;
        Descripcion = descripcion;
    }

    public String getClv_Aparato() {
        return Clv_Aparato;
    }

    public String getDescripcion() {
        return Descripcion;
    }
}
