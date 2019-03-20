package com.example.modulos_utiles.Procesos;

import android.app.ProgressDialog;
import android.content.Context;

public class BarraCarga {
    public ProgressDialog BarraCarga(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Cargando...");
        return progressDialog;
    }
}
