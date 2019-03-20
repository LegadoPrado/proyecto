package com.example.modulos_utiles.APIs;

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
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_ServiciosAparatos implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/MuestraServiciosRelTipoAparato/GetMuestraServiciosRelTipoAparatoList";
    private String token;
    private String usuario;

    public API_ServiciosAparatos(Context contexto) {

        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioServiciosAparatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioServiciosAparatos() throws JSONException {
        URL = URL.replace(" ", "%20");
        JSONObject obj = new JSONObject();
        //obj.put("Id", idarticuloAsig);
        JSONObject todo = new JSONObject();
        todo.put("obj",obj);
        //todo.put("Lst", jsonarray);

        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, todo, this, this) {
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

        String Clv_UnicaNet = null;
        String nombre = null;

        try {

            JSONArray jsonArray = response.getJSONArray("GetMuestraServiciosRelTipoAparatoListResult");
            JSONObject jsonObject = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Clv_UnicaNet = String.valueOf(jsonObject.getInt("Clv_UnicaNet"));
                nombre = jsonObject.getString("nombre");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }