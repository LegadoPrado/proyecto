package com.example.modulos_utiles.Objetos;

public class Reporte {
    private int CLV_TRABAJO;
    private String DESCRIPCION;

    public Reporte(int CLV_TRABAJO, String DESCRIPCION) {
        this.CLV_TRABAJO = CLV_TRABAJO;
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getCLV_TRABAJO() {
        return CLV_TRABAJO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }
}

