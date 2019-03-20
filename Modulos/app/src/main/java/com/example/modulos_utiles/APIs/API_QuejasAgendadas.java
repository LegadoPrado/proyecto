package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.Activity.Reportes;
import com.example.modulos_utiles.Adaptadores.Adaptador_Reportes;
import com.example.modulos_utiles.Objetos.QuejasAgendadas;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_QuejasAgendadas implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnKeyListener, View.OnClickListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private List<QuejasAgendadas> oAList;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/AplicacionMovil/GetDameListadoQuejasAgendadas";
    private View view;
    private String clv_Tecnico;
    private String token;
    private SharedPreferences preferences;

    public API_QuejasAgendadas(View view, Context contexto, String filtro, String clv_Queja,  String contrato) {

        this.view = view;
        this.contexto = contexto;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.clv_Tecnico = Util.getClvTecnicoPreference(preferences);
        this.token = Util.getTokenPreference(preferences);
        requestQueue = Volley.newRequestQueue(this.contexto);
        try {
            servicioQuejaAgendada(clv_Tecnico, filtro, clv_Queja, contrato);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioQuejaAgendada(String clv_Tecnico, String filtro, String clv_Queja, String contrato) throws JSONException {
        URL = URL.replace(" ", "%20");
        JSONObject jsonObject = new JSONObject();
        JSONObject ObjLista = new JSONObject();
        jsonObject.put("clv_tecnico", clv_Tecnico);
        jsonObject.put("op", filtro);
        jsonObject.put("clv_queja", clv_Queja);
        jsonObject.put("contratoCom", contrato);
        ObjLista.put("ObjLista", jsonObject);

        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, ObjLista, this, this) {
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
            oAList = new ArrayList<>();
            JSONArray jsonArray = response.optJSONArray("GetDameListadoQuejasAgendadasResult");
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                oAList.add(new QuejasAgendadas(
                        jsonObject.getInt("BaseIdUser"),
                        jsonObject.getString("BaseRemoteIp"),
                        jsonObject.getInt("Clv_Queja"),
                        jsonObject.getString("Contrato"),
                        jsonObject.getString("NOMBRE"),
                        jsonObject.getString("Status")
                ));
            }
            recicler(oAList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager manejador;
    private EditText edt_Queja;
    private EditText edt_Contrato;
    private ImageView icon_BuscarQueja;
    private ImageView icon_BuscarNContrato;

    private void recicler(List<QuejasAgendadas> lista) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edt_Contrato = (EditText) view.findViewById(R.id.edt_Contrato);
        edt_Queja = (EditText) view.findViewById(R.id.edt_Queja);
        icon_BuscarQueja = (ImageView) view.findViewById(R.id.icon_BuscarQueja);
        icon_BuscarNContrato = (ImageView) view.findViewById(R.id.icon_BuscarNContrato);

        manejador = new LinearLayoutManager(contexto);
        adaptador = new Adaptador_Reportes(contexto, lista, R.layout.recycler_oq, new Adaptador_Reportes.OnItemClickListener() {
            @Override
            //////////////////////////////////////////////////////////////////////////////////////
            public void click(String Contratocom, int clv) {
                Toast.makeText(contexto, Contratocom+" ,"+clv, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contexto, Reportes.class);


                SharedPreferences contratocom = contexto. getSharedPreferences("datosTempCliente",Context.MODE_PRIVATE);
                        SharedPreferences.Editor contarto= contratocom.edit();
                        contarto.putString("Contratocomp",Contratocom);
                        contarto.putInt("clv",clv);
                        contarto.commit();
            ////////////////////////////////////////////////////////////////////////////////////////
                contexto.startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(manejador);
        recyclerView.setAdapter(adaptador);
        edt_Queja.setOnKeyListener(this);
        edt_Contrato.setOnKeyListener(this);
        icon_BuscarNContrato.setOnClickListener(this);
        icon_BuscarQueja.setOnClickListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v == edt_Queja) {
            if (!edt_Queja.getText().toString().equals("")) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_QuejasAgendadas(view, contexto, "2", edt_Queja.getText().toString(), edt_Contrato.getText().toString());
                    return true;
                }
            } else {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_QuejasAgendadas(view, contexto, "1", "0", edt_Contrato.getText().toString());
                    return true;
                }
            }
        }
        if (v == edt_Contrato) {
            if (!edt_Contrato.getText().toString().equals("")) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_QuejasAgendadas(view, contexto, "3", "0", edt_Contrato.getText().toString());
                    return true;
                }
            } else {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_QuejasAgendadas(view, contexto, "1", "0", edt_Contrato.getText().toString());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == icon_BuscarNContrato) {
            if (!edt_Contrato.getText().toString().equals("")) {
                new API_QuejasAgendadas(view, contexto, "3", "0", edt_Contrato.getText().toString());
            } else {
                new API_QuejasAgendadas(view, contexto, "1", "0", edt_Contrato.getText().toString());
            }
        }
        if (v == icon_BuscarQueja) {
            if (!edt_Queja.getText().toString().equals("")) {
                new API_QuejasAgendadas(view, contexto, "2", edt_Queja.getText().toString(), edt_Contrato.getText().toString());
            } else {
                new API_QuejasAgendadas(view, contexto, "1", "0", edt_Contrato.getText().toString());
            }
        }
    }
}
