package com.example.modulos_utiles.Utils;

import android.content.SharedPreferences;

public class LimpiarDatos {

    public static void limpiarDatos(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
