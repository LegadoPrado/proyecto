package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.modulos_utiles.Objetos.AparatoDelCliente;
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

public class API_AparatodelCliente implements Response.Listener<JSONObject>, Response.ErrorListener {
    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/MuestraAparatosDisponibles/GetListClienteAparatos";
    private String token;
    private String usuario;
    private View view;

    public API_AparatodelCliente(Context contexto, View view) {

        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioAparatodelCliente();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioAparatodelCliente() throws JSONException {
        SharedPreferences preferences = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        URL = URL.replace(" ", "%20");
        JSONObject clv_Usuario = new JSONObject();
        clv_Usuario.put("Contrato", DatosTempCliente.get_contratoPreference(preferences));
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

    private String idArticulo = null;

    @Override
    public void onResponse(JSONObject response) {
        String ControNet = null;
        String Descripcion = null;

        String Mac = "Seleccione Aparato,";
        String Clv_Aparato = null;
        final List<AparatoDelCliente> lista = new ArrayList<>();

        try {

            JSONArray jsonArray = response.getJSONArray("GetListClienteAparatosResult");
            JSONObject jsonObject = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                ControNet = String.valueOf(jsonObject.getInt("ControNet"));
                Descripcion = jsonObject.getString("Descripcion");
                idArticulo = String.valueOf(jsonObject.getInt("idArticulo"));
                Mac += jsonObject.getString("Mac") + ",";
                Clv_Aparato = jsonObject.getString("Clv_Aparato");
                lista.add(new AparatoDelCliente(ControNet, Descripcion, String.valueOf(jsonObject.getInt("idArticulo")), Mac, Clv_Aparato));
            }
            String[] datos = Mac.split(",");
            final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_Sparato);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item, datos);
            spinner.setAdapter(adapter);

            final View vista = this.view;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spinner.getSelectedItemPosition() != 0) {

                        new API_AparatosDispByIdArt(contexto, vista, lista.get(position-1).getIdArticulo());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
