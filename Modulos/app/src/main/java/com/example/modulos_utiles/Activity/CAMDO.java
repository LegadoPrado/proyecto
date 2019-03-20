package com.example.modulos_utiles.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.modulos_utiles.APIs.API_CambioDom;
import com.example.modulos_utiles.R;

public class CAMDO extends AppCompatActivity implements View.OnClickListener {

    private TextView arriba, derecha, abajo, izquierda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camdo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        abajo = (TextView) findViewById(R.id.dirInf);
        abajo.setOnClickListener(this);
        arriba = (TextView) findViewById(R.id.dirSup);
        arriba.setOnClickListener(this);
        derecha = (TextView) findViewById(R.id.dirDer);
        derecha.setOnClickListener(this);
        izquierda = (TextView) findViewById(R.id.dirIzq);
        izquierda.setOnClickListener(this);
        new API_CambioDom(this, getWindow().getDecorView().getRootView(), getIntent().getStringExtra("clave"));
    }


    @Override
    public void onClick(View v) {
        if (v == abajo) {
            abajo.setHorizontallyScrolling(true);
            abajo.setFocusableInTouchMode(true);
            abajo.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            abajo.setMarqueeRepeatLimit(1);
        }
        if (v == arriba) {
            arriba.setHorizontallyScrolling(true);
            arriba.setFocusableInTouchMode(true);
            arriba.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            arriba.setMarqueeRepeatLimit(1);
        }
        if (v == derecha) {
            derecha.setHorizontallyScrolling(true);
            derecha.setFocusableInTouchMode(true);
            derecha.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            derecha.setMarqueeRepeatLimit(1);
        }
        if (v == izquierda) {
            izquierda.setHorizontallyScrolling(true);
            izquierda.setFocusableInTouchMode(true);
            izquierda.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            izquierda.setMarqueeRepeatLimit(1);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
