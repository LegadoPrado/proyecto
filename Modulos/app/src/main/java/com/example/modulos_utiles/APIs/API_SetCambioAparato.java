package com.example.modulos_utiles.APIs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import android.widget.Switch;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_SetCambioAparato implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/MuestraAparatosDisponibles/GetSetCambioAparato";
    private String token;
    private String usuario;

    public API_SetCambioAparato(Context contexto) {

        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioClvTecnico();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioClvTecnico() throws JSONException {
        URL = URL.replace(" ", "%20");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        //jsonObject.put("ClvAparato", CambioAparato.clvAparatoCAPAT);
        //jsonObject.put("ClvOrden", clvor);
        //jsonObject.put("ContratoNet", CambioAparato.contrato);
        //jsonObject.put("Status", CambioAparato.statusAparato);
        jsonObject.put("Trabajo", "CAPAT");
        jsonObject1.put("ObjCambioAparato", jsonObject);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject1, this, this) {
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
        Toast.makeText(contexto, "Error de autentificación " + error, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(contexto, "Se ha guardado el aparato correctamente", Toast.LENGTH_SHORT).show();

    }
}
