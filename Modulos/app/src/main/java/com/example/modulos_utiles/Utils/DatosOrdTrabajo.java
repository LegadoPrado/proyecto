package com.example.modulos_utiles.Utils;

import android.content.SharedPreferences;

//datosOrdTrabajo
public class DatosOrdTrabajo {


    //Datos del tap Trabajo
    public static String get_array(SharedPreferences preferences){
        return preferences.getString("array", "");
    }

    //Datos del tap Horas

    public static boolean isVisita(SharedPreferences preferences){
        return preferences.getBoolean("rb_visita", true);
    }
    public static boolean isEjecutada(SharedPreferences preferences){
        return preferences.getBoolean("rb_ejecutada", false);
    }
    public static String get_Visita1(SharedPreferences preferences){
        return preferences.getString("visita1", "");
    }
    public static String get_Visita2(SharedPreferences preferences){
        return preferences.getString("visita2", "");
    }
    public static String get_HoraInicioE(SharedPreferences preferences){
        return preferences.getString("horainicioe", "");
    }
    public static String get_HoraFinE(SharedPreferences preferences){
        return preferences.getString("horafine", "");
    }
    public static String get_Ejecucion(SharedPreferences preferences){
        return preferences.getString("ejecucion", "");
    }
    public static String get_HoraInicioV(SharedPreferences preferences){
        return preferences.getString("horainiciov", "");
    }
    public static String get_HoraFinV(SharedPreferences preferences){
        return preferences.getString("horafinv", "");
    }
    public static String get_TecnicoSecundario(SharedPreferences preferences){
        return preferences.getString("tecnicosecundario", "a:0");
    }
    public static String get_Observaciones(SharedPreferences preferences){
        return preferences.getString("observaciones", "");
    }
    public static String get_Latitud(SharedPreferences preferences){
        return preferences.getString("latitud", "");
    }
    public static String get_Longitud(SharedPreferences preferences){
        return preferences.getString("longitud", "");
    }
}
