package com.example.modulos_utiles.Objetos;

public class listaDeRecibidos {

    private int clave;
    private int clv_Orden;
    private int clv_Trabajo;
    private String descripcion;
    private String obs;
    private boolean seRealiza;
    private boolean recibi;

    public listaDeRecibidos(int clave, int clv_Orden, int clv_Trabajo, String descripcion, String obs, boolean seRealiza, boolean recibi) {
        this.clave = clave;
        this.clv_Orden = clv_Orden;
        this.clv_Trabajo = clv_Trabajo;
        this.descripcion = descripcion;
        this.obs = obs;
        this.seRealiza = seRealiza;
        this.recibi = recibi;
    }

    public void setRecibi(boolean recibi) {
        this.recibi = recibi;
    }

    public int getClave() {
        return clave;
    }

    public int getClv_Orden() {
        return clv_Orden;
    }

    public int getClv_Trabajo() {
        return clv_Trabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getObs() {
        return obs;
    }

    public boolean isSeRealiza() {
        return seRealiza;
    }

    public boolean isRecibi() {
        return recibi;
    }
}
