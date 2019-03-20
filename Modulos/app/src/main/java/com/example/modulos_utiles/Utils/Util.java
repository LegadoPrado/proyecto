package com.example.modulos_utiles.Utils;

import android.content.SharedPreferences;

//credenciales
public class Util {

    public static String getUsuarioPreference(SharedPreferences preferences){
        return preferences.getString("usuario", "");
    }

    public static String getContraseniaPreference(SharedPreferences preferences){
        return preferences.getString("contrasenia", "");
    }

    public static String getTokenPreference(SharedPreferences preferences){
        return preferences.getString("token", "");
    }

    public static String getClvTecnicoPreference(SharedPreferences preferences){
        return preferences.getString("clv_Tecnico", "");
    }

    public static String getNombreTecnicoPreference(SharedPreferences preferences){
        return preferences.getString("nombre_Tecnico", "");
    }

    public static boolean getRecordarPreference(SharedPreferences preferences){
        return preferences.getBoolean("recordar", false);
    }
}
