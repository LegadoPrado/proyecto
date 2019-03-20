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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_ConsultaIp implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/OrdSer/GetConsultaIpPorContrato";
    private String token;
    private String usuario;

    public API_ConsultaIp(Context contexto) {

        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioConsultaIp();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioConsultaIp() throws JSONException {
        URL = URL.replace(" ", "%20");
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        //jsonObject.put("contrato", DeepConsModel.Contrato);
        jsonObject1.put("ObjNodo", jsonObject);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject1, this, this) {
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
        String AplicaReintentar = null;
        String Msg = null;
        try {
            JSONObject jsonObject = response.getJSONObject("LogOnResult");
            AplicaReintentar = jsonObject.getString("AplicaReintentar");
            Msg = jsonObject.getString("Msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
     /*   reintentarComando = String.valueOf(user.AplicaReintentar);
        msgComando = user.Msg;



        for(int a=0;a<1;a++){

            if(reintentarComando.equals("true")){
                EjecutarFragment.reiniciar.setEnabled(true);
                EjecutarFragment.msgEjecutarOrd.setText(Request.msgComando);
            }else{
                if(msgComando.length()>3){
                    EjecutarFragment.msgEjecutarOrd.setText(msgComando);
                    Login.esperar(5);
                    ((Activity)context).finish();
                }else{
                    Login.esperar(3);
                    retry(call);
                }
            }*/

    }
}