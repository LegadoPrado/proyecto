package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_MedSer implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String URL1 = "http://192.168.50.100:8081/MuestraMedioPorServicoContratado/GetMuestraMedioPorServicoContratadoList";
    private String token;
    /////////////////////////////////////
    private String contrato;
    ////////////////////////////////////
    private View view;

    public API_MedSer(Context contexto, View view) {

        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.preferences2 = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.contrato = DatosTempCliente.get_contratoPreference(preferences2);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            API_DatosCambioAP();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void API_DatosCambioAP() throws JSONException {

        URL1 = URL1.replace(" ", "%20");
        JSONObject clv_Tecnico = new JSONObject();

        clv_Tecnico.put("ClvUnicaNet", 0);

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

    private String a= "";
    private String b= "";
    private String c= "";
    private String d= "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.names());
        JSONObject jsonObject = null;
        switch (String.valueOf(response.names())) {
            case "[\"-------------\"]":
                try {
                    JSONArray jsonArray = response.optJSONArray("-------------------------");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        a += jsonObject.getString("");
                        b += jsonObject.getString("");
                        c += jsonObject.getString("");
                        d += jsonObject.getString("");





                    }



                    servicioDatosAP();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }



    private void servicioDatosAP() {


    }

}
