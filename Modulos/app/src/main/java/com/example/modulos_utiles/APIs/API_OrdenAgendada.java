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
import com.example.modulos_utiles.Activity.Ordenes;
import com.example.modulos_utiles.Adaptadores.Adaptador_Ordenes;
import com.example.modulos_utiles.Objetos.OrdenesAgendadas;
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

public class API_OrdenAgendada implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnKeyListener, View.OnClickListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private List<OrdenesAgendadas> oAList;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/AplicacionMovil/GetDameListadoOrdenesAgendadas";
    private View view;
    private String clv_Tecnico;
    private String token;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private SharedPreferences.Editor editor;

    public API_OrdenAgendada(View view, Context contexto, String filtro, String clv_Orden, String contrato) {

        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.preferences2 = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.view = view;
        this.contexto = contexto;
        if (this.clv_Tecnico == null & token == null) {
            this.clv_Tecnico = Util.getClvTecnicoPreference(preferences);
            this.token = Util.getTokenPreference(preferences);
        }
        requestQueue = Volley.newRequestQueue(this.contexto);
        try {
            servicioOrdenAgendada(clv_Tecnico, filtro, clv_Orden, contrato);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioOrdenAgendada(String clv_Tecnico, String filtro, String clv_Orden, String contrato) throws JSONException {

        URL = URL.replace(" ", "%20");
        JSONObject jsonObject = new JSONObject();
        JSONObject ObjLista = new JSONObject();
        jsonObject.put("clv_tecnico", clv_Tecnico);
        jsonObject.put("op", filtro);
        jsonObject.put("clv_orden", clv_Orden);
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
            JSONArray jsonArray = response.optJSONArray("GetDameListadoOrdenesAgendadasResult");
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                oAList.add(new OrdenesAgendadas(
                        jsonObject.getInt("BaseIdUser"),
                        jsonObject.getString("BaseRemoteIp"),
                        jsonObject.getInt("Clv_Orden"),
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
    private EditText edt_Orden;
    private EditText edt_Contrato;
    private ImageView icon_BuscarOrden;
    private ImageView icon_BuscarNContrato;

    private void recicler(final List<OrdenesAgendadas> lista) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edt_Contrato = (EditText) view.findViewById(R.id.edt_Contrato);
        edt_Orden = (EditText) view.findViewById(R.id.edt_Orden);
        icon_BuscarOrden = (ImageView) view.findViewById(R.id.icon_BuscarOrden);
        icon_BuscarNContrato = (ImageView) view.findViewById(R.id.icon_BuscarNContrato);
        editor = preferences2.edit();

        manejador = new LinearLayoutManager(contexto);
        adaptador = new Adaptador_Ordenes(contexto, lista, R.layout.recycler_oq, new Adaptador_Ordenes.OnItemClickListener() {
            @Override
            public void click(String contrato, String status, String orden) {
                Intent intent = new Intent(contexto, Ordenes.class);
                intent.putExtra("estatus", status);
                intent.putExtra("nContrato", contrato);
                editor.putString("clv_OrdenRVO", orden);
                editor.commit();
                contexto.startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(manejador);
        recyclerView.setAdapter(adaptador);
        icon_BuscarNContrato.setOnClickListener(this);
        icon_BuscarOrden.setOnClickListener(this);
        edt_Orden.setOnKeyListener(this);
        edt_Contrato.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v == edt_Orden) {
            if (!edt_Orden.getText().toString().equals("")) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_OrdenAgendada(view, contexto, "2", edt_Orden.getText().toString(), "");
                    return true;
                }
            } else {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_OrdenAgendada(view, contexto, "1", "0", "");
                    return true;
                }
            }
        }
        if (v == edt_Contrato) {
            if (!edt_Contrato.getText().toString().equals("")) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_OrdenAgendada(view, contexto, "3", edt_Orden.getText().toString(), edt_Contrato.getText().toString());
                    return true;
                }
            } else {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new API_OrdenAgendada(view, contexto, "1", "0", "");
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
                new API_OrdenAgendada(view, contexto, "3", "0", edt_Contrato.getText().toString());
            } else {
                new API_OrdenAgendada(view, contexto, "1", "0", "");
            }
        }
        if (v == icon_BuscarOrden) {
            if (!edt_Orden.getText().toString().equals("")) {
                new API_OrdenAgendada(view, contexto, "2", edt_Orden.getText().toString(), "");
            } else {
                new API_OrdenAgendada(view, contexto, "1", "0", "");
            }
        }
    }
}
