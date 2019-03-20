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
import com.example.modulos_utiles.Objetos.TecnicoSecundario;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_TecnicoSec implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferencesTemp;
    private String token;
    private String usuario;
    private String claveOrden;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/MuestraRelOrdenesTecnicos/GetMuestraRelOrdenesTecnicosList";
    private List<TecnicoSecundario> lista;
    private View view;

    public API_TecnicoSec(Context contexto, View view) {

        this.view = view;
        this.preferencesTemp = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        this.claveOrden = DatosTempCliente.get_clv_OrdenRVPreference(preferencesTemp);

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
        jsonObject.put("ClvOrdSer", claveOrden);
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
        Toast.makeText(contexto, "Error de autentificación " + error, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {

        try {

            JSONArray jsonArray = response.optJSONArray("GetMuestraRelOrdenesTecnicosListResult");
            JSONObject jsonObject;
            lista = new ArrayList<>();
            String datos = "Seleccione un técnico secundario,";

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                lista.add(new TecnicoSecundario(
                        jsonObject.getInt("CLV_TECNICO"),
                        jsonObject.getString("NOMBRE")));
                datos += jsonObject.getString("NOMBRE") + ",";
            }
            cargarSpinner(datos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Spinner spinner;
    private void cargarSpinner(String datos){
        spinner = (Spinner) view.findViewById(R.id.spinnerTecnicoSec);
        String [] arreglo = datos.split(",");
        ArrayAdapter<String> spin = new ArrayAdapter<String>(contexto, R.layout.item_spinner_efren, arreglo);
        spinner.setAdapter(spin);
    }
}

