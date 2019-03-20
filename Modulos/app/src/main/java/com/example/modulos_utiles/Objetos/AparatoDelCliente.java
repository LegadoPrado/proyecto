package com.example.modulos_utiles.Objetos;

public class AparatoDelCliente {

    private String ControNet;
    private String Descripcion;
    private String idArticulo;
    private String Mac;
    private String Clv_Aparato;

    public AparatoDelCliente(String controNet, String descripcion, String idArticulo, String mac, String clv_Aparato) {
        ControNet = controNet;
        Descripcion = descripcion;
        this.idArticulo = idArticulo;
        Mac = mac;
        Clv_Aparato = clv_Aparato;
    }

    public String getControNet() {
        return ControNet;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public String getMac() {
        return Mac;
    }

    public String getClv_Aparato() {
        return Clv_Aparato;
    }
}
