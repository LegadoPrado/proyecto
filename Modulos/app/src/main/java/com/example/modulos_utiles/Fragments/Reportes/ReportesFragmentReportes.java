package com.example.modulos_utiles.Fragments.Reportes;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modulos_utiles.APIs.API_Reporte;
import com.example.modulos_utiles.APIs.API_TipoSolucion;
import com.example.modulos_utiles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportesFragmentReportes extends Fragment {


    public ReportesFragmentReportes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes_fragment_reportes, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Carga de info Reportes
        new API_Reporte(getActivity(), view);
        new API_TipoSolucion(getActivity(), view);
    }


}


