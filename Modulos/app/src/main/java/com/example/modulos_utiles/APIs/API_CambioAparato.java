package com.example.modulos_utiles.APIs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_CambioAparato implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String URL1 = "http://192.168.50.100:8081/SoftvWCFService.svc/MuestraAparatosDisponibles/GetCambioAparatoDeep";
    private String token;
    /////////////////////////////////////MuestraArbolServiciosAparatosPorinstalar/GetMuestraArbolServiciosAparatosPorinstalarList
    private String contrato;
    ////////////////////////////////////
    private View view;
    private String clave;

    public API_CambioAparato(Context contexto, View view,String clave) {

        this.clave = clave;
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

        clv_Tecnico.put("clv_orden", contrato);
        clv_Tecnico.put("Clave",clave);

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

    private String aparatoAsig= "";
    private String aparatoCli= "";
    private String status= "";
    private String tipoAp= "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.names());
        JSONObject jsonObject = null;
        switch (String.valueOf(response.names())) {
            case "[\"GetCambioAparatoDeepResult\"]":
                try {
                    jsonObject = response.getJSONObject("GetCambioAparatoDeepResult");

                    aparatoAsig = jsonObject.getString("AparatoAsignar");
                    aparatoCli = jsonObject.getString("AparatoCliente");
                    status = jsonObject.getString("StatusEntrega");
                    tipoAp = jsonObject.getString("TipoAparatoAsignar");

                    servicioDatosAP();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void servicioDatosAP() {

        if (aparatoAsig.equals("null") & aparatoCli.equals("null") & status.equals("null") & tipoAp.equals("null")){
            Toast.makeText(contexto, "null", Toast.LENGTH_SHORT).show();
        }else{
            //AparatoAsignar ultimo
            Spinner spn1, spn2, spn3;
            spn1 = (Spinner) view.findViewById(R.id.spinner_Sparato);
            spn2 = (Spinner) view.findViewById(R.id.spinner_EdoAparato);
            spn3 = (Spinner) view.findViewById(R.id.spinner_AparatoAsignado);

            String[] dato1 = {aparatoCli};
            ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item, dato1);
            spn1.setAdapter(adapter1);

            String[] dato2 = {status};
            ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item, dato2);
            spn2.setAdapter(adapter2);

            String[] dato3 = {aparatoCli};
            ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_item, dato3);
            spn3.setAdapter(adapter3);
        }
    }

}
