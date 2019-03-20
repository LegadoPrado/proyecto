package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_SiguienteCita implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/AplicacionMovil/GetDameSiguienteCita";
    private String token;
    private String clv_tecnico;
    private View view;

    public API_SiguienteCita(Context contexto, View view) {
        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.clv_tecnico = Util.getClvTecnicoPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(this.contexto);
        try {
            servicioSiguienteCita();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioSiguienteCita() throws JSONException {

        URL = URL.replace(" ", "%20");
        JSONObject clv_Tecnico = new JSONObject();
        clv_Tecnico.put("clv_tecnico", clv_tecnico);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, clv_Tecnico, this, this) {
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
        Toast.makeText(contexto, "Error de autentificación", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {

        //int BaseIdUser;
        //String BaseRemoteIp;
        String Calle;
        //int Clave;
        String Colonia;
        String Contrato;
        String Hora;
        int numero;
        String Tipo;


        JSONObject jsonObject = null;
        try {
            jsonObject = response.getJSONObject("GetDameSiguienteCitaResult");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //BaseIdUser = jsonObject.getInt("BaseIdUser");
            //BaseRemoteIp = jsonObject.getString("BaseRemoteIp");
            Calle = jsonObject.getString("Calle");
            //Clave = jsonObject.getInt("Clave");
            Colonia = jsonObject.getString("Colonia");
            Contrato = jsonObject.getString("Contrato");
            Hora = jsonObject.getString("Hora");
            numero = jsonObject.getInt("NUMERO");
            Tipo = jsonObject.getString("Tipo");

            TextView tv_tipo, tv_contrato, tv_hora, tv_calle, tv_numero, tv_colonia;

            tv_tipo = view.findViewById(R.id.tipoDeTrabajo);
            tv_contrato = view.findViewById(R.id.contrato);
            tv_hora = view.findViewById(R.id.hora);
            tv_calle = view.findViewById(R.id.calle);
            tv_numero = view.findViewById(R.id.numero);
            tv_colonia = view.findViewById(R.id.colonia);

            tv_calle.setText(Calle);
            tv_colonia.setText(Colonia);
            tv_contrato.setText(Contrato);
            tv_hora.setText(Hora);
            tv_numero.setText(String.valueOf(numero));
            tv_tipo.setText(Tipo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}