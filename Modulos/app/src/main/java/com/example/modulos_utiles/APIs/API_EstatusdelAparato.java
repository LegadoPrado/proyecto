package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.Objetos.estadoAparatos;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_EstatusdelAparato implements Response.Listener<JSONObject>, Response.ErrorListener {
    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/MuestraAparatosDisponibles/GetSP_StatusAparatosList";
    private String token;
    private String usuario;
    private View view;

    public API_EstatusdelAparato(Context contexto, View view) {

        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioEstatusdelAparato();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioEstatusdelAparato() throws JSONException {
        URL = URL.replace(" ", "%20");
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, this, this) {
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
    @Override
    public void onResponse(JSONObject response) {
        String Clv_StatusCableModem = null;
        String Concepto = "Seleccione un estado,";
        List<estadoAparatos> list = new ArrayList<>();
        try {

            JSONArray jsonArray = response.getJSONArray("GetSP_StatusAparatosListResult");
            JSONObject jsonObject = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Clv_StatusCableModem = jsonObject.getString("Clv_StatusCableModem");
                Concepto += jsonObject.getString("Concepto") + ",";

                list.add(new estadoAparatos(Clv_StatusCableModem, jsonObject.getString("Concepto")));
            }
            String [] datos = Concepto.split(",");
            Spinner spinner = (Spinner) view.findViewById(R.id.spinner_EdoAparato);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item, datos);
            spinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }
