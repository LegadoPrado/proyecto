package com.example.modulos_utiles.Utils;

import android.content.SharedPreferences;

//nombre del archivo datosTempCliente
public class DatosTempCliente {

  // public static String get_clvOrdenPreference(SharedPreferences preferences){
    //    return preferences.getString("clv_Orden", "");
    //}
    public static String get_contratoPreference(SharedPreferences preferences){
        return preferences.getString("contrato", "");
    }
    public static String get_fechaSolPreference(SharedPreferences preferences){
        return preferences.getString("fecha_Sol", "");
    }
    public static String get_ObsPreference(SharedPreferences preferences){
        return preferences.getString("obs", "");
    }
    public static String get_contratoCompPreference(SharedPreferences preferences){
        return preferences.getString("contratoComp", "");
    }
    public static String get_clv_OrdenRVPreference(SharedPreferences preferences){
        return preferences.getString("clv_OrdenRVO", "");
    }

    public static String get_Clave(SharedPreferences preferences){
        return preferences.getString("claveOrden", "Error");
    }

    //Brayan
    public static String get_contratoCompRPreference(SharedPreferences preferences) {
        return preferences.getString("Contratocomp", "");
    }

    public static int get_clv_OrdenReporte(SharedPreferences preferences) {
        return preferences.getInt("clv", 0);
    }
    //REPORTES
    public static int get_Reporte(SharedPreferences preferences){
        return preferences.getInt("reporte", 0);
    }
    public static String get_Contrato_Reporte(SharedPreferences preferences){
        return preferences.getString("contrato_reporte", "");
    }
}
