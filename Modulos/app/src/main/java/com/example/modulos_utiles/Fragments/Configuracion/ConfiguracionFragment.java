package com.example.modulos_utiles.Fragments.Configuracion;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.modulos_utiles.Activity.Login;
import com.example.modulos_utiles.R;
import com.example.modulos_utiles.Utils.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracionFragment extends Fragment implements View.OnClickListener {

    private Button btnCerrarSesion;
    private SharedPreferences preferences;
    private TextView nombreTecnico;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCerrarSesion = (Button) view.findViewById(R.id.btnCerrarSesion);
        nombreTecnico = (TextView) view.findViewById(R.id.nombreTecnico);
        btnCerrarSesion.setOnClickListener(this);
        preferences = getContext().getSharedPreferences("credenciales", getContext().MODE_PRIVATE);
        nombreTecnico.setText(Util.getNombreTecnicoPreference(preferences));
    }

    @Override
    public void onClick(View v) {
        if (v == btnCerrarSesion) {
            preferences.edit().clear().apply();
            Intent intent = new Intent(getContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(intent);
        }
    }
}
