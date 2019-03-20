package com.example.modulos_utiles.Fragments.Reportes;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modulos_utiles.APIs.API_TecnicoSecReportes;
import com.example.modulos_utiles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorasFragmentReportes extends Fragment {


    public HorasFragmentReportes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_horas_fragment_reportes, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences("datosTempCliente", Context.MODE_PRIVATE);



        //Carga espiner de técnicos secundarios
        new API_TecnicoSecReportes(getActivity(), view);
    }
}