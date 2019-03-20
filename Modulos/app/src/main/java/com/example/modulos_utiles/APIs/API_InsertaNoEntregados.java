package com.example.modulos_utiles.APIs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.Procesos.BarraCarga;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_InsertaNoEntregados implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private String token;
    private String URL = "http://192.168.50.100:8081/SoftvWCFService.svc/SP_InsertaTbl_NoEntregados/GetSP_InsertaTbl_NoEntregados";
    private JSONObject jsonObject;
    private ProgressDialog dialog;

    public API_InsertaNoEntregados(Context contexto, JSONObject jsonObject) {

        dialog = new BarraCarga().BarraCarga(contexto);
        dialog.show();
        this.contexto = contexto;
        this.jsonObject = jsonObject;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioInsertaNoEntregados();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioInsertaNoEntregados() throws JSONException {

        URL = URL.replace(" ", "%20");
        System.out.println(jsonObject);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(objectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.dismiss();
        try {
            System.out.println(error.networkResponse.getClass().getName());
            if (error.networkResponse.statusCode == 417) {
                Toast.makeText(contexto, "Duplicidad de clave primaria\nError (" + error.networkResponse.statusCode + ")", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(contexto,"No se puede establecer comunicación con el servidor" +
                    " revise su conexión a internet y reintente", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        dialog.dismiss();
        ((Activity)contexto).finish();
    }
}
