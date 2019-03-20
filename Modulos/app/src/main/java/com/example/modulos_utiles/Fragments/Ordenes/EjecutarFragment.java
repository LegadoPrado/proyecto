package com.example.modulos_utiles.Fragments.Ordenes;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.modulos_utiles.APIs.API_InsertaNoEntregados;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.DatosOrdTrabajo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EjecutarFragment extends Fragment {

    private SharedPreferences preferences;

    public EjecutarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ejecutar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button botonEjecutar = (Button) view.findViewById(R.id.ejecutar);
        Button botonSalir = (Button) view.findViewById(R.id.btn_Salir);
        Button botonReiniciar = (Button) view.findViewById(R.id.btn_Reiniciar);
        preferences = getActivity().getSharedPreferences("datosOrdTrabajo", Context.MODE_PRIVATE);

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getActivity()).finish();
            }
        });
        botonEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    try {
                        JSONArray jsonObjectProcesado = new JSONArray(DatosOrdTrabajo.get_array(preferences).replace("\\", ""));
                        JSONObject jsonObjectFinal = new JSONObject();
                        jsonObjectFinal.put("objSP_InsertaTbl_NoEntregados", jsonObjectProcesado);
                        new API_InsertaNoEntregados(getActivity(), jsonObjectFinal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean validar() {
        if (DatosOrdTrabajo.isEjecutada(preferences)) {
            if (DatosOrdTrabajo.get_Ejecucion(preferences).equals("")) {
                Toast.makeText(getActivity(), "Debe seleccionar la fecha de ejecución", Toast.LENGTH_LONG).show();
                return false;
            }
            if (DatosOrdTrabajo.get_HoraInicioE(preferences).equals("")) {
                Toast.makeText(getActivity(), "Debe seleccionar una hora de inicio para la ejecución", Toast.LENGTH_LONG).show();
                return false;
            }
            if (DatosOrdTrabajo.get_HoraFinE(preferences).equals("")){
                Toast.makeText(getActivity(), "Debe seleccionar una hora de termino para la ejecución", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        if (DatosOrdTrabajo.isVisita(preferences)) {
            if (DatosOrdTrabajo.get_Visita1(preferences).equals("")) {
                Toast.makeText(getActivity(), "Debe seleccionar la fecha de la visita", Toast.LENGTH_LONG).show();
                return false;
            }
            if (DatosOrdTrabajo.get_HoraInicioV(preferences).equals("")) {
                Toast.makeText(getActivity(), "Debe seleccionar una hora de inicio de la visita", Toast.LENGTH_LONG).show();
                return false;
            }
            if (DatosOrdTrabajo.get_HoraFinV(preferences).equals("")){
                Toast.makeText(getActivity(), "Debe seleccionar una hora de termino de la visita", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    return false;
    }
}
