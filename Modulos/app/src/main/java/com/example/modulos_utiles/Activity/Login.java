package com.example.modulos_utiles.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.modulos_utiles.APIs.API_Login;
import com.example.modulos_utiles.APIs.API_OrdenAgendada;
import com.example.modulos_utiles.Procesos.BarraCarga;
import com.example.modulos_utiles.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edt_Usuario;
    private EditText edt_Contrasenia;
    private Switch switchRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_Usuario = (EditText) findViewById(R.id.usuario);
        edt_Contrasenia = (EditText) findViewById(R.id.contrasenia);
        switchRecordar = (Switch) findViewById(R.id.recordar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            if (!edt_Usuario.getText().toString().isEmpty() & !edt_Contrasenia.getText().toString().isEmpty()) {
                new API_Login(this, edt_Usuario.getText().toString(), edt_Contrasenia.getText().toString(), switchRecordar);
            } else {
                Toast.makeText(this, "Llenar ambos campos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
