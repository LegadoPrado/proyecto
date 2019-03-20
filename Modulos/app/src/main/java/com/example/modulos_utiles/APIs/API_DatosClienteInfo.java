package com.example.modulos_utiles.APIs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
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
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_DatosClienteInfo implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String URL1 = "http://192.168.50.100:8082/SoftvWCFService.svc/BUSCLIPORCONTRATO_OrdSer/GetDeepBUSCLIPORCONTRATO_OrdSer";
    private String URL2 = "http://192.168.50.100:8082/SoftvWCFService.svc/AplicacionMovil/GetdameSerDELCliresumen";
    private String token;
    private String clv_tecnico;
    private View view;

    public API_DatosClienteInfo(Context contexto, View view) {

        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.preferences2 = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.clv_tecnico = Util.getClvTecnicoPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(this.contexto);
        try {
            servicioDatosClienteInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioDatosClienteInfo() throws JSONException {

        URL1 = URL1.replace(" ", "%20");
        JSONObject clv_Tecnico = new JSONObject();
        clv_Tecnico.put("CONTRATO", DatosTempCliente.get_contratoPreference(preferences2));
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL1, clv_Tecnico, this, this) {
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
        Toast.makeText(contexto, "Error " + error, Toast.LENGTH_SHORT).show();
    }

    private String Calle;
    private String ciudad;
    private String Colonia;
    private String Contrato;
    private String compania;
    private String nombre;
    private String servicios = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        JSONObject jsonObject = null;
        switch (String.valueOf(response.names())) {
            case "[\"GetDeepBUSCLIPORCONTRATO_OrdSerResult\"]":
                try {
                    jsonObject = response.getJSONObject("GetDeepBUSCLIPORCONTRATO_OrdSerResult");

                    Calle = jsonObject.getString("CALLE");
                    ciudad = jsonObject.getString("CIUDAD");
                    Colonia = jsonObject.getString("COLONIA");
                    Contrato = String.valueOf(jsonObject.getInt("CONTRATO"));
                    compania = jsonObject.getString("Compania");
                    nombre = jsonObject.getString("NOMBRE");

                    servicioDatosServicios();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case "[\"GetdameSerDELCliresumenResult\"]":
                try {
                    JSONArray jsonArray = response.optJSONArray("GetdameSerDELCliresumenResult");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        servicios += jsonObject.getString("Resumen") + "\n\n";
                    }
                    mostrarInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void servicioDatosServicios() throws JSONException {

        URL2 = URL2.replace(" ", "%20");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Contrato", DatosTempCliente.get_contratoPreference(preferences2));
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL2, jsonObject, this, this) {
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

    private TextView tv_compania, tv_ciudad, tv_nombre, tv_calle, tv_colonia, tv_Servicios;
    private ImageView cerrar;
    private Dialog dialog;

    private void mostrarInfo() {

        dialog = new Dialog(contexto);
        dialog.setContentView(R.layout.informacion_cliente);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cerrar = (ImageView) dialog.findViewById(R.id.btn_Cerrar);
        cerrar.setOnClickListener(this);
        tv_compania = (TextView) dialog.findViewById(R.id.tv_Compania);
        tv_ciudad = (TextView) dialog.findViewById(R.id.tv_Ciudad);
        tv_nombre = (TextView) dialog.findViewById(R.id.tv_Nombre);
        tv_calle = (TextView) dialog.findViewById(R.id.tv_Calle);
        tv_colonia = (TextView) dialog.findViewById(R.id.tv_Colonia);
        tv_Servicios = (TextView) dialog.findViewById(R.id.tv_Servicios);

        tv_calle.setText(Calle);
        tv_ciudad.setText(ciudad);
        tv_colonia.setText(Colonia);
        tv_compania.setText(compania);
        tv_nombre.setText(nombre);
        tv_Servicios.setText(servicios);

        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v == cerrar) {
            dialog.hide();
        }
    }
}