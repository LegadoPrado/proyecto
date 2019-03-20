package com.example.modulos_utiles.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.modulos_utiles.Fragments.Ordenes.CAPAG.CAPAT;
import com.example.modulos_utiles.Fragments.Ordenes.CAPAG.FTTH;
import com.example.modulos_utiles.R;

public class CAPAG extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String pantalla = getIntent().getStringExtra("pantalla");
        Fragment fragment = null;

        if (pantalla.equals("1")) {
            fragment = new FTTH();
        }
        if (pantalla.equals("2")) {
            fragment = new CAPAT();
        }
        if (fragment != null) {
            changeFragment(fragment);
        }
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
