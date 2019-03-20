package com.example.modulos_utiles.Objetos;

public class OrdenesAgendadas {

    private int baseIdUser;
    private String baseRemoteIp;
    private int clv_Orden;
    private String contrato;
    private String nombre;
    private String status;

    public OrdenesAgendadas(int baseIdUser, String baseRemoteIp, int clv_Orden, String contrato, String nombre, String status) {
        this.baseIdUser = baseIdUser;
        this.baseRemoteIp = baseRemoteIp;
        this.clv_Orden = clv_Orden;
        this.contrato = contrato;
        this.nombre = nombre;
        this.status = status;
    }

    public int getBaseIdUser() {
        return baseIdUser;
    }

    public String getBaseRemoteIp() {
        return baseRemoteIp;
    }

    public int getClv_Orden() {
        return clv_Orden;
    }

    public String getContrato() {
        return contrato;
    }

    public String getNombre() {
        return nombre;
    }

    public String getStatus() {
        return status;
    }
}