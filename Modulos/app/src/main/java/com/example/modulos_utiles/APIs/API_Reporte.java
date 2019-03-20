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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_Reporte implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String URL1 = "http://192.168.50.100:8082/SoftvWCFService.svc/Quejas/GetQuejasList";
    private String token;
    private int Clv;
    private View view;

    public API_Reporte(Context contexto, View view) {

        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.preferences2 = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.contexto = contexto;
        this.Clv = DatosTempCliente.get_clv_OrdenReporte(preferences2);
        requestQueue = Volley.newRequestQueue(contexto);
        try {
            API_Reporte();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void API_Reporte() throws JSONException {

        URL1 = URL1.replace(" ", "%20");
        JSONObject clv_Tecnico = new JSONObject();
        clv_Tecnico.put("Clv_Queja", Clv);
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

    private String ClasProblema = "";
    private String prioridad = "";
    private String Observaciones = "";
    private String Problema = "";
    private String contratoCom="";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.names());
        JSONObject jsonObject = null;
        switch (String.valueOf(response.names())) {
            case "[\"GetQuejasListResult\"]":
                try {
                    JSONArray jsonArray = response.optJSONArray("GetQuejasListResult");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        ClasProblema = jsonObject.getString("ClasificacionProblema");
                        prioridad = jsonObject.getString("Prioridad");
                        Observaciones = jsonObject.getString("Observaciones");
                        Problema = jsonObject.getString("Problema");
                        contratoCom=jsonObject.getString("ContratoCom");


                    }

                    servicioDatosServicios();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private TextView tv_clasp,tv_prior,tv_problm,tv_obs,tv_contrato;


    private void servicioDatosServicios() {

tv_clasp=view.findViewById(R.id.tv_clasProblm);
tv_clasp.setText(ClasProblema);
tv_prior=view.findViewById(R.id.tv_clasPrior);
tv_prior.setText(prioridad);
tv_problm=view.findViewById(R.id.tv_ReporteCliente);
tv_problm.setText(Problema);
tv_obs=view.findViewById(R.id.tv_ObservacionesCliente);
tv_obs.setText(Observaciones);


//tv_contrato=view.findViewById(R.id.tv_NContrato1);
//tv_contrato.setText(contratoCom);


    }


}