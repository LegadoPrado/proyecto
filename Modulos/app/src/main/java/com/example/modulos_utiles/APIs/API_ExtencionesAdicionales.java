package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.Objetos.DetalleOrdServList;
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_ExtencionesAdicionales implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {
    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferencesTemp;
    private SharedPreferences.Editor editor;
    private String token;
    private String usuario;
    private String claveOrden;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/OrdSer/GetCONCONEX";
    private List<DetalleOrdServList> lista;
    private View view;

    public API_ExtencionesAdicionales(Context contexto, View view) {

        this.view = view;
        this.preferencesTemp = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        this.claveOrden = DatosTempCliente.get_clv_OrdenRVPreference(preferencesTemp);

        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioExtencionesAdicionales();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioExtencionesAdicionales() throws JSONException {
        URL = URL.replace(" ", "%20");
        JSONObject extenciones = new JSONObject();
        //extenciones.put("Clave", claveTrabajo);
        extenciones.put("Clv_Orden", claveOrden);
        //extenciones.put("Contrato", contrato);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, extenciones, this, this) {
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
    public void onClick(View v) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(contexto, "Error de autentificación " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        String extenciones;
        try {
            response.getInt("GetCONCONEXResult");
            extenciones= String.valueOf(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
