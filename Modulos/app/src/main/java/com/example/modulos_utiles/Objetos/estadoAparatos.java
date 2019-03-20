package com.example.modulos_utiles.Objetos;

public class estadoAparatos {

    private String Clv_StatusCableModem;
    private String Concepto;

    public estadoAparatos(String clv_StatusCableModem, String concepto) {
        Clv_StatusCableModem = clv_StatusCableModem;
        Concepto = concepto;
    }

    public String getClv_StatusCableModem() {
        return Clv_StatusCableModem;
    }

    public String getConcepto() {
        return Concepto;
    }
}
