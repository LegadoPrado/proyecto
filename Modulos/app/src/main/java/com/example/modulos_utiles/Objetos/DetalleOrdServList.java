package com.example.modulos_utiles.Objetos;

public class DetalleOrdServList {

    private String Accion;
    private int Clave;
    private int Clv_Orden;
    private int Clv_Trabajo;
    private String Descripcion;
    private String Obs;
    private boolean SeRealiza;

    public DetalleOrdServList(String accion, int clave, int clv_Orden, int clv_Trabajo, String descripcion, String obs, boolean seRealiza) {
        Accion = accion;
        Clave = clave;
        Clv_Orden = clv_Orden;
        Clv_Trabajo = clv_Trabajo;
        Descripcion = descripcion;
        Obs = obs;
        SeRealiza = seRealiza;
    }

    public String getAccion() {
        return Accion;
    }

    public int getClave() {
        return Clave;
    }

    public int getClv_Orden() {
        return Clv_Orden;
    }

    public int getClv_Trabajo() {
        return Clv_Trabajo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getObs() {
        return Obs;
    }

    public boolean isSeRealiza() {
        return SeRealiza;
    }
}
