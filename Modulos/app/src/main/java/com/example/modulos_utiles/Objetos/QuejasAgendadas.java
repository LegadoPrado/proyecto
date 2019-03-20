package com.example.modulos_utiles.Objetos;

public class QuejasAgendadas {

    private int baseIdUser;
    private String baseRemoteIp;
    private int clv_Queja;
    private String contrato;
    private String nombre;
    private String status;

    public QuejasAgendadas(int baseIdUser, String baseRemoteIp, int clv_Queja, String contrato, String nombre, String status) {
        this.baseIdUser = baseIdUser;
        this.baseRemoteIp = baseRemoteIp;
        this.clv_Queja = clv_Queja;
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

    public int getClv_Queja() {
        return clv_Queja;
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
