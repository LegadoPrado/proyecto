package com.example.modulos_utiles.APIs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.modulos_utiles.Activity.Principal;
import com.example.modulos_utiles.Procesos.BarraCarga;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_Login implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean recordar;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/Usuario/LogOn";
    private ProgressDialog dialog;

    public API_Login(Context contexto, String usuario, String contrasenia, Switch recordar) {
        dialog = new BarraCarga().BarraCarga(contexto);
        dialog.show();
        this.recordar = recordar.isChecked();
        preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        saveOnPreference(usuario, contrasenia);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(this.contexto);
        servicioLogin(usuario, contrasenia);
    }

    private void saveOnPreference(String usuario, String contrasenia) {
        editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contrasenia", contrasenia);
        //Sincrono
        editor.commit();
        //Asincrono
        //editor.apply();
    }

    private void servicioLogin(final String usuario, final String contrasenia) {
        URL = URL.replace(" ", "%20");
        objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", usuario, contrasenia);
                String auth = "Basic: " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        requestQueue.add(objectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(contexto, "Error: login " + error, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        String token = null;
        try {
            JSONObject jsonObject = response.getJSONObject("LogOnResult");
            token = jsonObject.getString("Token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (token != null) {
            Intent intent = new Intent(contexto, Principal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            editor = preferences.edit();
            editor.putString("token", token);
            editor.putBoolean("recordar", recordar);
            editor.commit();
            dialog.dismiss();
            new API_ClvTecnico(contexto);
            contexto.startActivity(intent);
        } else {
            Toast.makeText(contexto, "Error al obtener el token", Toast.LENGTH_SHORT).show();
        }
    }
}
