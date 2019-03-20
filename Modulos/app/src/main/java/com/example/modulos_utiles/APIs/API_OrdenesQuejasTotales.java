package com.example.modulos_utiles.APIs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.modulos_utiles.Objetos.quejasOrdenesTotales;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class API_OrdenesQuejasTotales implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;
    private JsonObjectRequest objectRequest;
    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String URL = "http://192.168.50.100:8082/SoftvWCFService.svc/AplicacionMovil/GetDameOrdenesQuejasTotales";
    private String token;
    private String clv_tecnico;
    private View view;
    private int oE, oP, oV, oO, oEP, qE, qP, qV, qO, qEP;

    public API_OrdenesQuejasTotales(Context contexto, View view) {
        this.view = view;
        this.preferences = contexto.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        this.token = Util.getTokenPreference(preferences);
        this.clv_tecnico = Util.getClvTecnicoPreference(preferences);
        this.contexto = contexto;
        requestQueue = Volley.newRequestQueue(this.contexto);
        try {
            servicioSiguienteCita();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void servicioSiguienteCita() throws JSONException {

        URL = URL.replace(" ", "%20");
        JSONObject clv_Tecnico = new JSONObject();
        clv_Tecnico.put("clv_tecnico", clv_tecnico);
        objectRequest = new JsonObjectRequest(Request.Method.POST, URL, clv_Tecnico, this, this) {
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
        Toast.makeText(contexto, "Error de autentificación", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(JSONObject response) {

        JSONObject jsonObject = null;

        try {
            jsonObject = response.getJSONObject("GetDameOrdenesQuejasTotalesResult");

            for (int i = 0; i < jsonObject.getJSONArray("OrdSer").length(); i++) {
                //ordSer.setBaseIdUser(jsonObject.getJSONArray("OrdSer").getJSONObject(i).getString("BaseIdUser"));
                //ordSer.setBaseRemoteIp((jsonObject.getJSONArray("OrdSer").getJSONObject(i).getString("BaseRemoteIp")));
                String estatus = jsonObject.getJSONArray("OrdSer").getJSONObject(i).getString("Status");

                switch (estatus) {
                    case "Ejecutada":
                        oE += jsonObject.getJSONArray("OrdSer").getJSONObject(i).getInt("Total");
                        break;
                    case "Pendiente":
                        oP += jsonObject.getJSONArray("OrdSer").getJSONObject(i).getInt("Total");
                        break;
                    case "Visita":
                        oV += jsonObject.getJSONArray("OrdSer").getJSONObject(i).getInt("Total");
                        break;
                    case "En proceso":
                        oEP += jsonObject.getJSONArray("OrdSer").getJSONObject(i).getInt("Total");
                        break;
                    case "otro":
                        oO += jsonObject.getJSONArray("OrdSer").getJSONObject(i).getInt("Total");
                        break;
                }
            }

            for (int j = 0; j < jsonObject.getJSONArray("Queja").length(); j++) {
//                queja.setBaseIdUser(jsonObject.getJSONArray("Queja").getJSONObject(j).getString("BaseIdUser"));
                //              queja.setBaseRemoteIp((jsonObject.getJSONArray("Queja").getJSONObject(j).getString("BaseRemoteIp")));
                String estatus = jsonObject.getJSONArray("Queja").getJSONObject(j).getString("Status");
                switch (estatus) {
                    case "Ejecutada":
                        qE += jsonObject.getJSONArray("Queja").getJSONObject(j).getInt("Total");
                        break;
                    case "Pendiente":
                        qP += jsonObject.getJSONArray("Queja").getJSONObject(j).getInt("Total");
                        break;
                    case "Visita":
                        qV += jsonObject.getJSONArray("Queja").getJSONObject(j).getInt("Total");
                        break;
                    case "En proceso":
                        qEP += jsonObject.getJSONArray("Queja").getJSONObject(j).getInt("Total");
                        break;
                    case "otro":
                        qO += jsonObject.getJSONArray("Queja").getJSONObject(j).getInt("Total");
                        break;
                }
            }
           graficaDePastel();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private PieChart grafica;
    private List<PieEntry> listaPie = new ArrayList<>();

    private void graficaDePastel() {
        grafica = (PieChart) view.findViewById(R.id.graficaPastel);
        if (listaPie.isEmpty()) {
            setListaPie();
            PieDataSet ldventas = new PieDataSet(listaPie, "");
            ldventas.setSliceSpace(10);
            ldventas.setSelectionShift(5f);
            //ldventas.setHighlightEnabled(true);
            //ldventas.setColors(ColorTemplate.COLORFUL_COLORS);
            ldventas.setColors(colores());
            ldventas.setValueTextSize(5);
            ldventas.setDrawValues(true);
            ldventas.setValueTextColor(Color.BLACK);

            PieData datos = new PieData(ldventas);
            datos.setDrawValues(true);
            datos.setValueTextSize(20f);
            datos.setValueTextColor(Color.BLACK);

            Description description = new Description();
            description.setText("");
            description.setTextColor(Color.BLACK);
            description.setTextSize(18);

            grafica.setDescription(description);
            grafica.setData(datos);

            grafica.setDrawEntryLabels(true);
            grafica.setDrawCenterText(false);
            grafica.setEntryLabelTextSize(14);
            grafica.setCenterTextSize(5);
            grafica.setDrawHoleEnabled(false);
            grafica.setEntryLabelColor(Color.BLACK);
            //grafica.setUsePercentValues(true);
            grafica.highlightValue(null);
            grafica.getLegend().setTextColor(Color.BLACK);
            grafica.getLegend().setTextSize(14f);
            grafica.getLegend().setEnabled(false);
            grafica.animateXY(2000, 2000);
            grafica.invalidate();
        }
    }

    private void setListaPie() {
        if (oE != 0) listaPie.add(new PieEntry(oE, "Orden ejecutada"));
        if (oP != 0) listaPie.add(new PieEntry(oP, "Orden pendiente"));
        if (oV != 0) listaPie.add(new PieEntry(oV, "Orden visita"));
        if (oEP != 0) listaPie.add(new PieEntry(oEP, "Orden en proceso"));
        if (oO != 0) listaPie.add(new PieEntry(oO, "Orden otro"));
        if (qE != 0) listaPie.add(new PieEntry(qE, "Queja Ejecutada"));
        if (qP != 0) listaPie.add(new PieEntry(qP, "Queja Pendiente"));
        if (qO != 0) listaPie.add(new PieEntry(qO, "Agosto"));
        if (qV != 0) listaPie.add(new PieEntry(qV, "Septiembre"));
        if (qEP != 0) listaPie.add(new PieEntry(qEP, "Octubre"));
    }

    private int[] colores() {
        return new int[]{
                Color.parseColor("#E9967A"),
                Color.GREEN,
                Color.GRAY,
                Color.MAGENTA,
                Color.RED,
                Color.parseColor("#800000"),
                Color.parseColor("#000080"),
                Color.parseColor("#800080"),
                Color.parseColor("#808000"),
                Color.parseColor("#008080")};
    }

}