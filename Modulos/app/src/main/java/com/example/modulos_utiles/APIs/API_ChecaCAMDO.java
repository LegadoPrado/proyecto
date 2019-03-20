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

public class API_ChecaCAMDO implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/Checa_si_tiene_camdo/GetCheca_si_tiene_camdo";
    private String token;
    private String usuario;

    public API_ChecaCAMDO(Context contexto) {

        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            servicioChecaCAMDO();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioChecaCAMDO() throws JSONException {
        URL = URL.replace(" ", "%20");
        JSONObject obj1 = new JSONObject();
        //obj1.put("ClvOrden",clvord);

        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, obj1, this, this) {
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
        String Error=null;
        try {
            JSONObject jsonObject = response.getJSONObject("GetCheca_si_tiene_camdoResult");
            Error = jsonObject.getString("Error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Error.equals("0")){
            Toast.makeText(contexto, "Seguir " , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(contexto, "Error "+Error , Toast.LENGTH_SHORT).show();
        }

    }
}