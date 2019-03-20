package com.example.modulos_utiles.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.modulos_utiles.Activity.Login;
import com.example.modulos_utiles.Activity.Principal;
import com.example.modulos_utiles.Utils.Util;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Intent intentLogin = new Intent(this, Login.class);
        Intent intentMain = new Intent(this, Principal.class);

        if (!TextUtils.isEmpty(Util.getUsuarioPreference(preferences))
                && !TextUtils.isEmpty(Util.getContraseniaPreference(preferences))
                && !TextUtils.isEmpty(Util.getTokenPreference(preferences))
                && Util.getRecordarPreference(preferences)) {
            startActivity(intentMain);
        } else {
            startActivity(intentLogin);
        }
        finish();
    }
}
