package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modulos_utiles.Activity.CAMDO;
import com.example.modulos_utiles.Activity.CAPAG;
import com.example.modulos_utiles.Adaptadores.Adaptador_DetalleOrdServ;
import com.example.modulos_utiles.Objetos.DetalleOrdServList;
import com.example.modulos_utiles.Objetos.listaDeRecibidos;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.DatosTempCliente;
import com.example.modulos_utiles.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_DetalleOrdServ implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences preferencesTemp;
    private SharedPreferences.Editor editor;
    private String token;
    private String usuario;
    private String claveOrden;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/BUSCADetOrdSer/GetBUSCADetOrdSerList";
    private List<DetalleOrdServList> lista;
    private View view;
    private SharedPreferences preferencesJSONARRAY;

    public API_DetalleOrdServ() {
    }

    public API_DetalleOrdServ(Context contexto, View view) {

        preferencesJSONARRAY = contexto.getSharedPreferences("datosOrdTrabajo", Context.MODE_PRIVATE);
        editor = preferencesJSONARRAY.edit();
        this.view = view;
        this.preferencesTemp = contexto.getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        this.claveOrden = DatosTempCliente.get_clv_OrdenRVPreference(preferencesTemp);

        this.token = Util.getTokenPreference(preferences);
        this.usuario = Util.getUsuarioPreference(preferences);
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
        Toast.makeText(contexto, "Error de autentificación " + error, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {

        try {

            JSONArray jsonArray = response.optJSONArray("GetBUSCADetOrdSerListResult");
            JSONObject jsonObject;
            lista = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                lista.add(new DetalleOrdServList(
                        jsonObject.getString("Accion"),
                        jsonObject.getInt("Clave"),
                        jsonObject.getInt("Clv_Orden"),
                        jsonObject.getInt("Clv_Trabajo"),
                        jsonObject.getString("Descripcion"),
                        jsonObject.getString("Obs"),
                        jsonObject.getBoolean("SeRealiza")
                ));
            }
            llenarRecycler();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager manejador;
    private CheckBox checkBox;

    private void llenarRecycler() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        checkBox = (CheckBox) view.findViewById(R.id.check_recibi);

        manejador = new LinearLayoutManager(contexto);
        adaptador = new Adaptador_DetalleOrdServ(contexto, lista, R.layout.recycler_trabajo, new Adaptador_DetalleOrdServ.OnItemClickListener() {
            @Override
            public void click(int clave, boolean recibi, List<listaDeRecibidos> recibidos, int pos) {
                JSONObject objetos;
                final JSONArray arreglo = new JSONArray();

                for (listaDeRecibidos lista : recibidos) {
                    if (!lista.isRecibi()) {
                        try {
                            objetos = new JSONObject();
                            objetos.put("Clave", lista.getClave());
                            objetos.put("Clv_Orden", lista.getClv_Orden());
                            objetos.put("Clv_Trabajo", lista.getClv_Trabajo());
                            objetos.put("Descripcion", lista.getDescripcion());
                            objetos.put("Obs", lista.getObs());
                            objetos.put("SeRealiza", lista.isSeRealiza());
                            objetos.put("recibi", lista.isRecibi());
                            arreglo.put(objetos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                SharedPreferences.Editor editor = preferencesJSONARRAY.edit();
                editor.putString("array", String.valueOf(arreglo)).commit();

                if (recibidos.get(pos).getClv_Trabajo() == 16) {
                    Intent intent = new Intent(contexto, CAMDO.class);
                    intent.putExtra("clave", String.valueOf(recibidos.get(pos).getClave()));
                    contexto.startActivity(intent);
                }
                if (recibidos.get(pos).getClv_Trabajo() == 1204) {
                    Intent intent = new Intent(contexto, CAPAG.class);
                    intent.putExtra("clave", String.valueOf(recibidos.get(pos).getClave()));
                    intent.putExtra("pantalla", "1");
                    contexto.startActivity(intent);
                }
                if (recibidos.get(pos).getClv_Trabajo() == 1273) {
                    Intent intent = new Intent(contexto, CAPAG.class);
                    intent.putExtra("clave", String.valueOf(recibidos.get(pos).getClave()));
                    intent.putExtra("pantalla", "2");
                    contexto.startActivity(intent);
                }
            }
        });
        recyclerView.setLayoutManager(manejador);
        recyclerView.setAdapter(adaptador);
    }

}
