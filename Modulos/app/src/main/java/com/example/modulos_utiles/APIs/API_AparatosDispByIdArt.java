package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.example.modulos_utiles.Objetos.AparatosDisponibles;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_AparatosDispByIdArt implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/MuestraAparatosDisponibles/GetListAparatosDisponiblesByIdArticulo";
    private String token;
    private String usuario;
    private View view;
    private String id;

    public API_AparatosDispByIdArt(Context contexto, View view, String id) {

        this.id = id;
        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioApaDisponible();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioApaDisponible() throws JSONException {
        SharedPreferences preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        URL = URL.replace(" ", "%20");
        JSONObject clv_Usuario = new JSONObject();
        clv_Usuario.put("Clv_Tecnico", Util.getClvTecnicoPreference(preferences));
        clv_Usuario.put("Id_Articulo", id);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, clv_Usuario, this, this) {
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

        String Clv_Aparato = null;
        String Descripcion = "Seleccione un aparato,";
        List<AparatosDisponibles> list = new ArrayList();

        try {

            JSONArray jsonArray = response.getJSONArray("GetListAparatosDisponiblesByIdArticuloResult");
            JSONObject jsonObject = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Clv_Aparato = String.valueOf(jsonObject.getInt("Clv_Aparato"));
                Descripcion += jsonObject.getString("Descripcion") + ",";
                list.add(new AparatosDisponibles(Clv_Aparato, jsonObject.getString("Descripcion")));
            }
            String[] datos = Descripcion.split(",");
            System.out.println(datos.length);
            final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_AparatoAsignado);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item, datos);
            spinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}