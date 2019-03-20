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
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_DCOrdServ implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferencesTemp;
    private SharedPreferences.Editor editor;
    private String token;

    private String claveOrden;
    private View view;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/ConsultaOrdSer/GetDeepConsultaOrdSer";


    public API_DCOrdServ(View view, Context contexto) {

        this.preferencesTemp = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.claveOrden = DatosTempCliente.get_clv_OrdenRVPreference(preferencesTemp);
        this.view = view;

        this.token = Util.getTokenPreference(preferences);
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
        JSONObject clv_Usuario = new JSONObject();
        clv_Usuario.put("Clv_Orden", claveOrden);
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
        Toast.makeText(contexto, "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONObject jsonObject = response.getJSONObject("GetDeepConsultaOrdSerResult");
            editor = preferencesTemp.edit();
            //editor.putString("clv_Orden", String.valueOf(jsonObject.getInt("Clv_Orden")));
            editor.putString("contrato", String.valueOf(jsonObject.getInt("Contrato")));
            editor.putString("fecha_Sol", jsonObject.getString("Fec_Sol"));
            editor.putString("obs", jsonObject.getString("Obs"));
            editor.putString("contratoComp", jsonObject.getString("ContratoCom"));
            editor.apply();

            setearDatos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private TextView tv_Obs;

    private void setearDatos() {
        tv_Obs = (TextView) view.findViewById(R.id.tv_Observaciones);
        tv_Obs.setText(DatosTempCliente.get_ObsPreference(preferencesTemp));
    }
}
