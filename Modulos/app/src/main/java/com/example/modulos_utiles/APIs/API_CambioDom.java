package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.modulos_utiles.Servicios.servicios;
import com.example.modulos_utiles.Utils.DatosOrdTrabajo;
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_CambioDom implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String URL1 = new servicios().getCambioDomicilio();
    private String token;
    /////////////////////////////////////
    private String contrato;
    ////////////////////////////////////
    private View view;
    private String clave;

    public API_CambioDom(Context contexto, View view, String clave) {
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

        clv_Tecnico.put("clv_orden", DatosTempCliente.get_clv_OrdenRVPreference(preferences2));
        clv_Tecnico.put("Clave", clave);
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

    private String CalleEste= "";
    private String CalleNorte= "";
    private String CalleOeste= "";
    private String CalleSur= "";
    private String Casa= "";
    private String Sciudad, Slocalidad, Scolonia, Scalle, Snumero, SnumeroInt, Stelefono;
    private TextView ciudad, localidad, colonia, calle, numero, numeroInt, telefono, calleSup, calleInf, calleDer, calleIzq;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {
        JSONObject jsonObject;
        JSONArray jsonArray;
                try {
                    jsonArray = response.optJSONArray("GetDameDatosCAMDOResult");
                    jsonObject = jsonArray.getJSONObject(0);

                    Sciudad = jsonObject.getString("Ciudad");
                    Slocalidad = jsonObject.getString("localidad");
                    Scolonia = jsonObject.getString("colonia");
                    Scalle = jsonObject.getString("calle");
                    Snumero = jsonObject.getString("NUMERO");
                    SnumeroInt = jsonObject.getString("Num_int");
                    Stelefono = jsonObject.getString("TELEFONO");
                    CalleEste = jsonObject.getString("calleEste");
                    CalleNorte = jsonObject.getString("calleNorte");
                    CalleOeste = jsonObject.getString("calleOeste");
                    CalleSur = jsonObject.getString("calleSur");
                    Casa = jsonObject.getString("Casa");

                    servicioDatosAP();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    private ImageView N,S,E,O;
    private void servicioDatosAP() {
        N = (ImageView) view.findViewById(R.id.img_casaSup);
        S = (ImageView) view.findViewById(R.id.img_casaInf);
        E = (ImageView) view.findViewById(R.id.img_casaDer);
        O = (ImageView) view.findViewById(R.id.img_casaIzq);
        ciudad = (TextView) view.findViewById(R.id.tv_Ciudad);
        localidad = (TextView) view.findViewById(R.id.tv_Localidad);
        colonia = (TextView) view.findViewById(R.id.tv_Ciudad);
        calle = (TextView) view.findViewById(R.id.tv_Calle);
        numero = (TextView) view.findViewById(R.id.tv_Numero);
        numeroInt = (TextView) view.findViewById(R.id.tv_NumeroInterior);
        telefono = (TextView) view.findViewById(R.id.tv_Telefono);
        calleDer = (TextView) view.findViewById(R.id.dirDer);
        calleIzq = (TextView) view.findViewById(R.id.dirIzq);
        calleSup = (TextView) view.findViewById(R.id.dirSup);
        calleInf = (TextView) view.findViewById(R.id.dirInf);

        ciudad.setText(Sciudad);
        localidad.setText(Slocalidad);
        colonia.setText(Scolonia);
        calle.setText(Scalle);
        numero.setText(Snumero);
        numeroInt.setText(SnumeroInt);
        telefono.setText(Stelefono);
        calleDer.setText(CalleEste);
        calleIzq.setText(CalleOeste);
        calleSup.setText(CalleNorte);
        calleInf.setText(CalleSur);

        if (Casa.equalsIgnoreCase("N")){
            ocultarCasas();
            N.setVisibility(View.VISIBLE);
        }if (Casa.equalsIgnoreCase("S")){
            ocultarCasas();
            S.setVisibility(View.VISIBLE);
        }if (Casa.equalsIgnoreCase("E")){
            ocultarCasas();
            E.setVisibility(View.VISIBLE);
        }if (Casa.equalsIgnoreCase("o")){
            ocultarCasas();
            O.setVisibility(View.VISIBLE);
        }
    }
    void ocultarCasas(){
        N.setVisibility(View.INVISIBLE);
        S.setVisibility(View.INVISIBLE);
        E.setVisibility(View.INVISIBLE);
        O.setVisibility(View.INVISIBLE);
    }

}
